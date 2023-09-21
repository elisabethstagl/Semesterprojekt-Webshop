package com.webshop.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.webshop.demo.dto.LoginRequest;
import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.dto.TokenResponse;
import com.webshop.demo.model.User;
import com.webshop.demo.service.UserService;

import jakarta.validation.Valid;

/*Ein Controller ist eine Schicht in der Anwendungsarchitektur, 
die als Schnittstelle zwischen der Benutzeroberfläche und dem Backend dient. 
Es empfängt Anfragen von der Benutzeroberfläche und entscheidet, wie diese Anfragen verarbeitet werden sollen.
*/
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // READ
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping()
    public List<User> readAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User read(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // CREATE

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid RegistrationRequest registrationRequest,
            @RequestParam("profilePicture") MultipartFile profilePicture) {
        userService.register(registrationRequest, profilePicture);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<String> token = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        return token.map(TokenResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(null)); // Unauthorized
    }

    // UPDATE

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
