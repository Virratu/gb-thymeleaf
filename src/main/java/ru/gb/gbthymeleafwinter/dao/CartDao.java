package ru.gb.gbthymeleafwinter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbthymeleafwinter.entity.Cart;

import java.util.List;

public interface CartDao  extends JpaRepository<Cart, Long> {
    Cart findByUsername(String username);
}
