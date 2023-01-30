package com.webshop.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;

public class UserController {
    
    @Autowired
    private UserRepository userRepos;

    @GetMapping
    public List<User> findAllUser() {
        return userRepos.findAll();
    }
}
