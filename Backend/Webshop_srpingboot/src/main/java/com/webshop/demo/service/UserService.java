package com.webshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class UserService {
    

    private UserRepository userRepos;

    public UserService(UserRepository userRepos){
        this.userRepos = userRepos;
    }

    // METHODEN 

    public void deleteById(Long id) {
        userRepos.deleteById(id);
    }
    

    public User save(User user) {
        return userRepos.save(user);
    }

    public Optional<User> findById(Long id){
        return userRepos.findById(id);
    }

    public List<User> findAll(){
        return userRepos.findAll();
    }

    public User update(Long id, User updatedUser) {
        User user = userRepos.findById(id).orElseThrow(EntityNotFoundException::new);

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setLastName(updatedUser.getLastName());
        user.setFirstName(updatedUser.getFirstName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setCity(updatedUser.getCity());
        user.setDoornumber(updatedUser.getDoornumber());
        user.setPostalCode(updatedUser.getPostalCode());

        return userRepos.save(user);
    }
}
