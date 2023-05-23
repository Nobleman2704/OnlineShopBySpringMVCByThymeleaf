package uz.jk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/page")
public class PaginationController {

    @GetMapping("/get_seller_products_by_page/{page}")
    public String getByPage(@PathVariable("page") int page,
                            RedirectAttributes attributes){
        attributes.addAttribute("page", page);
        return "redirect:/product/get_seller_products";
    }

    @GetMapping("/get_my_orders_by_page/{page}")
    public String getMyOrdersByPage(@PathVariable("page") int page,
                                    RedirectAttributes attributes){
        attributes.addAttribute("page",page);
        return "redirect:/order/get_my_orders";
    }

    @GetMapping("/get_seller_orders_by_page/{page}")
    public String getSellerOrdersByPage(@PathVariable("page") int page,
                                    RedirectAttributes attributes){
        attributes.addAttribute("page",page);
        return "redirect:/order/get_seller_orders";
    }

    @GetMapping("/get_my_order_history_by_page/{page}")
    public String getMyOrderHistoryByPage(@PathVariable("page") int page,
                                    RedirectAttributes attributes){
        attributes.addAttribute("page",page);
        return "redirect:/history/get_my_order_history";
    }

    @GetMapping("/get_products_by_searching/{page}/{word}")
    public String getProductsBySearching(@PathVariable("page") int page,
                                    @PathVariable("word") String word,
                                    RedirectAttributes attributes){
        attributes.addAttribute("page",page);
        attributes.addAttribute("word", word);
        if (word.equals("GADGET") || word.equals("LAPTOP") || word.equals("PHONE")){
            return "redirect:/product/search_by_category";
        }
        return "redirect:/product/search_by_name";
    }
}
