package com.webshop.demo.service;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepos;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepos){
        this.shoppingCartRepos = shoppingCartRepos;
    }

    // METHODEN

    public ShoppingCart save(ShoppingCart shoppingCart){
        return shoppingCartRepos.save(shoppingCart);
    }

    public ShoppingCart findByUserId(Long userId) {
        return shoppingCartRepos.findByUserId(userId);
    }

    }

