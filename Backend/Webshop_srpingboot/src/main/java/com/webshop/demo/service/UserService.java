package com.webshop.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.dto.UserDTO;
import com.webshop.demo.model.User;
import com.webshop.demo.model.UserRole;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil JwtUtil; // Inject JwtUtil

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtil JwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.JwtUtil = JwtUtil;
    }

    // Methode zur Registrierung eines neuen Benutzers
    public User register(RegistrationRequest registrationRequest) {

        // Überprüfen, ob bereits ein Benutzer mit dem gewünschten Benutzernamen existiert
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            // Falls ja, eine Laufzeitexception auslösen mit einer entsprechenden
            // Fehlermeldung
            throw new RuntimeException("Username already exists.");
        }

        // Das Klartextpasswort aus dem Registrierungsantrag mit einem Passwort-Encoder
        // hashen
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // Erstellen einer neuen Benutzerinstanz und Setzen der entsprechenden Felder
        // mit den Werten aus dem Registrierungsantrag
        User newUser = new User();
        newUser.setSex(registrationRequest.getSex());
        newUser.setFirstName(registrationRequest.getFirstName());
        newUser.setLastName(registrationRequest.getLastName());
        newUser.setAddress(registrationRequest.getAddress());
        newUser.setDoornumber(registrationRequest.getDoornumber());
        newUser.setPostalCode(registrationRequest.getPostalCode());
        newUser.setCity(registrationRequest.getCity());
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(hashedPassword); // Setzen des gehashten Passworts
        newUser.setRole(UserRole.USER); // Setzen der Benutzerrolle auf den Standardwert (USER)

        // Speichern des neuen Benutzers in der Datenbank und Rückgabe des gespeicherten
        // Benutzerobjekts
        return userRepository.save(newUser);
    }

    // Methode, um einen Benutzer zu authentifizieren und bei Erfolg einen JWT
    // zurückzugeben
    public Optional<String> authenticateUser(String username, String password) {
        // Suche nach einem Benutzer mit dem angegebenen Benutzernamen in der Datenbank
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Überprüfen, ob ein Benutzer mit dem angegebenen Benutzernamen gefunden wurde
        if (userOptional.isPresent()) {
            // Extrahieren des User-Objekts aus dem Optional
            User user = userOptional.get();

            // Überprüfen, ob das eingegebene Passwort mit dem in der Datenbank
            // gespeicherten Passwort übereinstimmt
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Wenn das Passwort korrekt ist, generiere einen JWT für den Benutzer
                String token = JwtUtil.generateToken(user);
                // Rückgabe des Tokens eingepackt in einem Optional-Objekt
                return Optional.of(token);
            }
        }

        // Wenn der Benutzername nicht gefunden wurde oder das Passwort nicht korrekt
        // ist, gib ein leeres Optional zurück
        return Optional.empty();
    }

    // METHODEN

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long id, UserDTO updatedUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUserDTO.getUsername());
        user.setLastName(updatedUserDTO.getLastName());
        user.setFirstName(updatedUserDTO.getFirstName());
        user.setEmail(updatedUserDTO.getEmail());
        user.setAddress(updatedUserDTO.getAddress());
        user.setCity(updatedUserDTO.getCity());
        user.setDoornumber(updatedUserDTO.getDoornumber());
        user.setPostalCode(updatedUserDTO.getPostalCode());
        user.setRole(updatedUserDTO.getRole());

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Versuche, einen Benutzer mit dem gegebenen Benutzernamen aus der Datenbank zu
        // holen
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Überprüfe, ob ein Benutzer gefunden wurde
        if (userOptional.isPresent()) {
            // Holen Sie sich das User-Objekt aus dem Optional
            User user = userOptional.get();

            // Konvertiere UserRole zu einer GrantedAuthority, um die Autorität/Rolle zu
            // repräsentieren
            // (fügt "ROLE_" als Präfix hinzu, was eine übliche Praxis in Spring Security
            // ist)
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            // Erstelle eine Sammlung von Autoritäten (Rollen), hier nur eine einzige Rolle
            Collection<GrantedAuthority> authorities = Collections.singleton(authority);

            // Erstelle ein UserDetails-Objekt mit Benutzerdetails und Rollen
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword()) // Verwende das gehashte Passwort aus der Datenbank
                    .authorities(authorities) // Setze die Rolle(n) des Benutzers
                    .build();

            // Protokolliere das erfolgreiche Abrufen des Benutzers
            logger.info("User found with username: {}", username);

            // Gib das UserDetails-Objekt zurück
            return userDetails;
        } else {
            // Protokolliere, dass der Benutzer nicht gefunden wurde
            logger.error("User not found with username: {}", username);
            // Wirf eine Ausnahme, wenn der Benutzer nicht gefunden wurde
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
