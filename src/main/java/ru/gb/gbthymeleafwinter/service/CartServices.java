package ru.gb.gbthymeleafwinter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbthymeleafwinter.dao.CartDao;
import ru.gb.gbthymeleafwinter.entity.Cart;
import ru.gb.gbthymeleafwinter.entity.Product;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServices {
    private final CartDao cartDao;

    public Set<Product> getCartProduct(Cart cart){
        return cart.getProducts();
    }

    public void addProductToCart(Cart cart, Product product){
        cart.addProduct(product);
        cartDao.save(cart);
    }

    public void deleteProductFromCart(Cart cart, Product product){
        cart.removeProduct(product);
        cartDao.save(cart);
    }

    public Cart findByUsername(String username){
        return cartDao.findByUsername(username);
    }
}
