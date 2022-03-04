package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbthymeleafwinter.dao.CartDao;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.service.CartServices;
import ru.gb.gbthymeleafwinter.service.ProductService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CartServices cartServices;

    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping("/cart")
    public String getCartList(Model model, Principal principal) {
        model.addAttribute("products", cartServices.getCartProduct(cartServices.findByUsername(principal.getName())));
        return "product-cart";
    }

    @GetMapping("/{productId}")
    public String info(Model model, @PathVariable(name = "productId") Long id) {
        Product product;
        if (id != null) {
            product = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", product);
        return "product-info";
    }

    @GetMapping
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Product product;

        if (id != null) {
            product = productService.findById(id);
        } else {
            product = new Product();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    @GetMapping("/cart/add")
    public String addProductToCart(@RequestParam(name = "id") Long id, Principal principal){

        if (principal == null) {
            return "auth-required";
        }
        cartServices.addProductToCart(cartServices.findByUsername(principal.getName()), productService.findById(id));
        return "redirect:/product/all";
    }

    @GetMapping("/cart/delete")
    public String deleteProductFromCart(@RequestParam(name = "id") Long id, Principal principal) {
        cartServices.deleteProductFromCart(cartServices.findByUsername(principal.getName()),productService.findById(id));
        return "redirect:/product/cart";
    }
}
