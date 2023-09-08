package com.webshop.demo.service;

import java.util.List;
import java.util.Optional;
import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.model.User;
import com.webshop.demo.model.User.UserRole;




/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepos){
        this.userRepository = userRepos;
    }

    // Add the registration method
    public User register(RegistrationRequest registrationRequest) {
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

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public List<User> findAll(){
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
}
