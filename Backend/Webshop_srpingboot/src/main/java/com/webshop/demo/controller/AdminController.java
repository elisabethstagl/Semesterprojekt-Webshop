package com.webshop.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webshop.demo.dto.UserDTO;
import com.webshop.demo.model.User;
import com.webshop.demo.service.AdminServiceImpl;
import com.webshop.demo.service.UserService;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    // Endpoint to list all registered users
    // Endpoint to list all registered users
    @GetMapping("/users")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        try {
            User user = userService.update(id, updatedUserDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Endpoint to get user details by ID
    @GetMapping("/users/{userId}")
    public User read(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    // Add more admin-specific endpoints and methods here

    // Example: Endpoint to promote a user to admin
    @PostMapping("/users/{userId}/promote")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long userId) {
        boolean promoted = adminServiceImpl.promoteUserToAdmin(userId);
        if (promoted) {
            return ResponseEntity.ok("User promoted to admin.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


