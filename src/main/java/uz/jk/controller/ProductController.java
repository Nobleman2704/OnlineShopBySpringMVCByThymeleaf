package uz.jk.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.jk.domain.dto.ProductCreateDto;
import uz.jk.domain.entity.product.ProductCategory;
import uz.jk.domain.entity.product.ProductEntity;
import uz.jk.domain.response.BaseResponse;
import uz.jk.service.product.ProductService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add_product")
    public String addProduct(@ModelAttribute ProductCreateDto productCreateDto,
                             HttpSession session,
                             RedirectAttributes attributes) {
        UUID userId = (UUID) session.getAttribute("userId");
        productCreateDto.setUserId(userId);
        BaseResponse baseResponse = productService.create(productCreateDto);
        attributes.addAttribute("message", baseResponse.getMessage());
        return "redirect:/product/get_seller_products";
    }

    @GetMapping("/search_window")
    public String displaySearchWindow() {
        return "search-window";
    }

    @GetMapping("/search_by_name")
    public String searchByName(@RequestParam(name = "word") String word,
                               @RequestParam(name = "page",
                                       defaultValue = "0")int page,
                               Model model) {
        BaseResponse<List<ProductEntity>> baseResponse = productService.findByProductName(word, page);
        model.addAttribute("products", baseResponse.getData());
        model.addAttribute("message", baseResponse.getMessage());
        model.addAttribute("word", word);
        model.addAttribute("pages", baseResponse.getTotalPages());
        return "search-window";
    }


    @GetMapping("/search_by_category")
    public String searchByCategory(@RequestParam(name = "word") ProductCategory category,
                                   @RequestParam(name = "page",
                                           defaultValue = "0")int page,
                                   Model model) {
        BaseResponse<List<ProductEntity>> baseResponse = productService.findByCategory(category, page);
        model.addAttribute("products", baseResponse.getData());
        model.addAttribute("message", baseResponse.getMessage());
        model.addAttribute("word", category);
        model.addAttribute("pages", baseResponse.getTotalPages());
        return "search-window";
    }


    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable UUID id,
                                Model model) {
        ProductEntity product = productService.findById(id);
        model.addAttribute("product", product);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute ProductCreateDto productCreateDto,
                         @PathVariable("id") UUID id) {
        productCreateDto.setId(id);
        productService.update(productCreateDto);
        return "redirect:/product/get_seller_products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable UUID id,
                                RedirectAttributes attributes) {
        BaseResponse delete = productService.delete(id);
        attributes.addAttribute("message", delete.getMessage());
        return "redirect:/product/get_seller_products";
    }

    @GetMapping("/get_seller_products")
    public String getProducts(Model model,
                              HttpSession session,
                              @RequestParam(name = "message",
                                      required = false) String message,
                              @RequestParam(name = "page",
                                      defaultValue = "0") int page) {
        UUID userId = (UUID) session.getAttribute("userId");
        BaseResponse<List<ProductEntity>> response = productService.findSellerProductsById(userId, page);
        model.addAttribute("products", response.getData());
        model.addAttribute("message", (message!=null)?message:response.getMessage());
        model.addAttribute("pages", response.getTotalPages());
        return "product-crud";
    }
}
