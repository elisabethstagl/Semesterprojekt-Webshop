package com.webshop.demo.SamuelTestZeug;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
public class FormController {
    
    @PostMapping("/submit")
    public ResponseEntity<String> submitForm(@RequestParam("name") String name,
                                           @RequestParam("email") String email) {
    // Process the form data
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);

    // Return a response to the frontend
    return ResponseEntity.ok("Form submitted successfully");
  }
}
