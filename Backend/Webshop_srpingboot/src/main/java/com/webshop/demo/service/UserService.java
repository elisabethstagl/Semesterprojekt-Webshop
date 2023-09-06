package com.webshop.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class UserService {

    private UserRepository userRepos;
    private PasswordEncoder passwordEncoder;

    // METHODEN

    @Autowired
    public UserService(UserRepository userRepos, PasswordEncoder passwordEncoder) {
        this.userRepos = userRepos;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepos.save(user);
    }

    public void deleteById(Long id) {
        userRepos.deleteById(id);
    }

    public Optional<User> findById(Long id){
        return userRepos.findById(id);
    }

    public List<User> findAll(){
        return userRepos.findAll();
    }

    public User update(Long id, User updatedUser) {
        User user = userRepos.findById(id).orElseThrow(EntityNotFoundException::new);

        if (updatedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        user.setUsername(updatedUser.getUsername());
        user.setLastName(updatedUser.getLastName());
        user.setFirstName(updatedUser.getFirstName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setCity(updatedUser.getCity());
        user.setDoornumber(updatedUser.getDoornumber());
        user.setPostalCode(updatedUser.getPostalCode());

        return userRepos.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepos.findByUsername(username);
    }
}
