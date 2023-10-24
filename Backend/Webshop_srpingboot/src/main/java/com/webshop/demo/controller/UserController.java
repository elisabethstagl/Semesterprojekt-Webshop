package com.webshop.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

        Object principal = auth.getPrincipal();
        System.out.println("Principal Type: " + principal.getClass().getName());
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            User user = userService.findByUsername(userDetails.getUsername());
            if (user != null) {
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    // Erlauben von Cross-Origin-Anfragen vom spezifizierten Ursprung
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    // Abbilden von HTTP POST-Anfragen auf den Endpunkt '/register'
    @PostMapping("/register")
    // Indizieren, dass die Methode eine ResponseEntity zurückgibt, die sowohl den
    // HTTP-Status als auch eine Map als Antwortkörper enthält
    public ResponseEntity<Map<String, String>> register(
            // Validieren des eingehenden Registrierungsanforderungs-Payloads
            @Valid RegistrationRequest registrationRequest) {

        // Registrieren des Benutzers über den Benutzerdienst mit den bereitgestellten
        // Registrierungsdetails und dem Profilbild
        userService.register(registrationRequest);

        // Erstellen einer Map, um die Antwortnachricht zu halten
        Map<String, String> response = new HashMap<>();
        // Hinzufügen einer Erfolgsmeldung zur Antwort-Map
        response.put("message", "Benutzer erfolgreich registriert");

        // Zurückgeben eines 200 OK HTTP-Status zusammen mit der Antwort-Map
        return ResponseEntity.ok(response);
    }

    // Erlauben von Cross-Origin-Anfragen von der spezifizierten Herkunft
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    // Zuordnen von HTTP POST-Anfragen auf den Endpunkt '/login'
    @PostMapping("/login")
    // Die Methode gibt eine ResponseEntity zurück, welche den HTTP-Status und den
    // Token als Antwortkörper enthält
    public ResponseEntity<TokenResponse> authenticateUser(
            // @RequestBody bindet den HTTP-Request-Body (also die JSON-Payload) an das
            // LoginRequest-Objekt
            @RequestBody LoginRequest loginRequest) {

        // Aufruf der authenticateUser-Methode des userService mit Benutzername und
        // Passwort aus dem LoginRequest
        // und Speicherung des zurückgegebenen Tokens (falls vorhanden) in einer
        // Optional-Variable
        Optional<String> token = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        // Rückgabe des Antwortobjekts:
        // Wenn ein Token vorhanden ist, wird es in ein TokenResponse-Objekt gewrappt
        // und mit dem HTTP-Status 200 OK zurückgegeben.
        // Wenn kein Token vorhanden ist, wird eine Antwort mit dem HTTP-Status 401
        // Unauthorized und ohne Körper zurückgegeben.
        return token.map(TokenResponse::new) // Umwandeln des Tokens in ein TokenResponse-Objekt, falls es vorhanden ist
                .map(ResponseEntity::ok) // Umwandeln des TokenResponse-Objekts in eine ResponseEntity mit Status 200 OK
                .orElseGet(() -> ResponseEntity.status(401).body(null)); // Falls kein Token vorhanden ist, Rückgabe
                                                                         // einer 401 Unauthorized-Antwort

    }

    // UPDATE

    // @PutMapping("/{id}")
    // public User update(@PathVariable Long id, @RequestBody User user) {
    // return userService.update(id, user);
    // }

    // DELETE

    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathVariable Long id) {
    // userService.deleteById(id);
    // }

}
