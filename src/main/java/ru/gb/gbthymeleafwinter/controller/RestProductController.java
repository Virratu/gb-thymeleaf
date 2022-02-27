package ru.gb.gbthymeleafwinter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbthymeleafwinter.entity.Product;
import ru.gb.gbthymeleafwinter.service.CartServices;
import ru.gb.gbthymeleafwinter.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class RestProductController {

    private final ProductService productService;
    private final CartServices cartServices;

    @GetMapping
    public List<Product> getProductList() {
        return productService.findAll();
    }

    @GetMapping("/cart")
    public Set<Product> getCartList() {
        return cartServices.getCartProduct(cartServices.findByUsername("user"));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<? extends Product> getProduct(@PathVariable("productId") Long id) {
        Product product;

        if (id != null) {
            product = productService.findById(id);
            if (product != null){
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/"+savedProduct.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @RequestBody Product product){
        product.setId(id);
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }

    @PutMapping("/cart/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToCart(@PathVariable("productId") Long id){
        cartServices.addProductToCart(cartServices.findByUsername("user"), productService.findById(id));
    }

    @DeleteMapping("/cart/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductFromCart(@PathVariable("productId") Long id) {
        cartServices.deleteProductFromCart(cartServices.findByUsername("user"),productService.findById(id));
    }
}
