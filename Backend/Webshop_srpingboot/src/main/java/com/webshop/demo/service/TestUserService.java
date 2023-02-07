package com.webshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.TestUsers;
import com.webshop.demo.repository.TestUserRepository;

@Service
public class TestUserService {
    

    private TestUserRepository testUserRepos;

    public TestUserService(TestUserRepository testUserRepos){
        this.testUserRepos = testUserRepos;
    }

    // METHODEN 

    public TestUsers save(TestUsers testUser) {
        return testUserRepos.save(testUser);
    }

//     public Optional<User> findById(Long id){
//         return userRepos.findById(id);
//     }

//     public List<User> findAll(){
//         return userRepos.findAll();
//     }

//     public User update(Long id, User updatedUser) {
//         User user = userRepos.findById(id).orElseThrow(EntityNotFoundException::new);

//         user.setUsername(updatedUser.getUsername());
//         user.setPassword(updatedUser.getPassword());
//         user.setLastName(updatedUser.getLastName());
//         user.setFirstName(updatedUser.getFirstName());
//         user.setEmail(updatedUser.getEmail());
//         user.setAddress(updatedUser.getAddress());

//         return userRepos.save(user);
//     }
}
