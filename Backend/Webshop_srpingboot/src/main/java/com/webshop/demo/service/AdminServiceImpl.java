package com.webshop.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webshop.demo.model.User;
import com.webshop.demo.model.UserRole;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    @Autowired
    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean promoteUserToAdmin(Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(UserRole.ADMIN);
            userService.save(user);
            return true;
        }
        return false;
    }

}
