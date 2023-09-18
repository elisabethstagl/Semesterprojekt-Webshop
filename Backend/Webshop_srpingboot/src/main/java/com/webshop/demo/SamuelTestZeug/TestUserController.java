package com.webshop.demo.SamuelTestZeug;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestUserController {

    private TestUserService testUserService;

    public TestUserController(TestUserService testUserService) {
        this.testUserService = testUserService;
    }

    // CREATE

    @PostMapping("/form-submit")
    @ResponseStatus(HttpStatus.CREATED)
    public TestUsers create(@RequestBody TestUsers testUser) {
    return testUserService.save(testUser);
    }

//     @PostMapping("/submit-form")
//     public ResponseEntity<FormData> submitForm(@RequestBody TestUsers testUsers) {
//     formRepository.save(testUsers);
//     return ResponseEntity.ok(testUsers);
//   }
}
