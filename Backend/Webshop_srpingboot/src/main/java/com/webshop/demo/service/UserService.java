package com.webshop.demo.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.security.JwtUtil;
import org.springframework.web.multipart.MultipartFile;
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
import com.webshop.demo.model.User;
import com.webshop.demo.model.User.UserRole;

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

    // Add the registration method
    public User register(RegistrationRequest registrationRequest, MultipartFile profilePicture) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        // Hashing the password
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // Creating a new user instance and setting fields
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
        newUser.setPassword(hashedPassword);
        newUser.setRole(UserRole.USER); // default role

        // Handle profile picture upload
        try {
            if (profilePicture != null && !profilePicture.isEmpty()) {
                newUser.setProfilePicture(profilePicture.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error uploading profile picture.");
        }

        return userRepository.save(newUser);
    }

    public Optional<String> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = JwtUtil.generateToken(user);
                return Optional.of(token);
            }
        }

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

    public User update(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setLastName(updatedUser.getLastName());
        user.setFirstName(updatedUser.getFirstName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setCity(updatedUser.getCity());
        user.setDoornumber(updatedUser.getDoornumber());
        user.setPostalCode(updatedUser.getPostalCode());

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Convert UserRole to a GrantedAuthority
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            // Create a collection of authorities (roles)
            Collection<GrantedAuthority> authorities = Collections.singleton(authority);

            // Create a UserDetails object with user details and roles
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Use the hashed password from your database
                .authorities(authorities)
                .build();

            // Log successful user retrieval
            logger.info("User found with username: {}", username);

            return userDetails;
        } else {
            // Log unsuccessful user retrieval
            logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
