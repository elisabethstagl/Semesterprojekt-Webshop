package com.webshop.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webshop.demo.dto.CurrentUserDTO;
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

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // READ
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/current-user")
    public CurrentUserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Entering getCurrentUser()");
        // The principal should be your user object, or its user details representation
        Object principal = auth.getPrincipal();
        System.out.println("Principal Type: " + principal.getClass().getName());
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            User user = userService.findByUsername(userDetails.getUsername());
            if(user != null){
                CurrentUserDTO currentUser = new CurrentUserDTO();
                currentUser.setId(user.getId());
                currentUser.setUsername(user.getUsername());
                currentUser.setRole(userDetails.getAuthorities().stream()
                                    .findFirst().orElseThrow(() -> new RuntimeException("No roles found for user."))
                                    .getAuthority());
                System.out.println("User found in getCurrentUser()");
                return currentUser;
            } else {
                System.out.println("Error: No user found in getCurrentUser()");
                throw new RuntimeException("Current user not found in database.");
            }
        }
        System.out.println("Invoked getCurrentUser()");
        // If principal is not an instance of UserDetails, throw an exception.
        throw new RuntimeException("Unable to get current user details.");
    }


    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping()
    public ResponseEntity<List<User>> readAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        System.out.println("Hello");
        return token.map(TokenResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(null)); // Unauthorized
                
    }

    // UPDATE

    // @PutMapping("/{id}")
    // public User update(@PathVariable Long id, @RequestBody User user) {
    //     return userService.update(id, user);
    // }

    // DELETE

    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathVariable Long id) {
    //     userService.deleteById(id);
    // }

}
