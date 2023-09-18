package com.webshop.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/some-resource")
    public ResponseEntity<String> adminOnlyResource() {
        // Your admin-specific code here
        return ResponseEntity.ok("Admin-only resource accessed.");
    }

    // Add more admin-specific endpoints and methods here
}

