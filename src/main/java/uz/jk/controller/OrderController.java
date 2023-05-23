package uz.jk.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.jk.domain.dto.OrderCreateDto;
import uz.jk.domain.entity.order.OrderEntity;
import uz.jk.domain.entity.order.OrderStatus;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.order.OrderService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add_order")
    public String addOrder(@ModelAttribute OrderCreateDto orderCreateDto,
                           Model model,
                           HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        orderCreateDto.setOwnerId(userId);
        BaseResponse baseResponse = orderService.create(orderCreateDto);
        model.addAttribute("message", baseResponse.getMessage());
        return "search-window";
    }

    @GetMapping("/edit_order_amount")
    public String editMyOrders() {
        return "redirect:/order/get_my_orders";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID uuid) {
        orderService.delete(uuid);
        return "redirect:/order/get_my_orders";
    }

    @PostMapping("/edit_order_amount")
    public String editOrderAmount(@RequestParam("id") UUID orderId,
                                  @RequestParam("amount") int amount) {
        System.out.println("orderId = " + orderId);
        System.out.println("amount = " + amount);
        orderService.changeOrderAmount(orderId, amount);
        return "redirect:/order/get_my_orders";
    }

    @PostMapping("/edit_order_status")
    public String editOrderStatus(@RequestParam("status") OrderStatus status,
                                  @RequestParam("id") UUID orderId,
                                  RedirectAttributes attributes) {
        BaseResponse baseResponse = orderService.changeOrderStatus(orderId, status);
        attributes.addAttribute("message", baseResponse.getMessage());
        return "redirect:/order/get_seller_orders";
    }

    @GetMapping("/get_seller_orders")
    public String getSellerOrder(Model model,
                                 HttpSession session,
                                 @RequestParam(name = "message",
                                         required = false) String message,
                                 @RequestParam(name = "page",
                                         defaultValue = "0") int page) {
        UUID userId = (UUID) session.getAttribute("userId");
        BaseResponse<List<OrderEntity>> response = orderService.getSellerOrders(userId, page);
        model.addAttribute("orders", response.getData());
        model.addAttribute("message", (message!=null)?message:response.getMessage());
        model.addAttribute("pages", response.getTotalPages());
        return "edit-order-status";
    }

    @GetMapping("/get_my_orders")
    public String getMyOrders(Model model,
                              HttpSession session,
                              @RequestParam(name = "page",
                                      defaultValue = "0") int page) {
        UUID userId = (UUID) session.getAttribute("userId");

        BaseResponse<List<OrderEntity>> response = orderService.findMyOrdersById(userId, page);
        model.addAttribute("orders", response.getData());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("pages", response.getTotalPages());
        return "order-crud";
    }
}
