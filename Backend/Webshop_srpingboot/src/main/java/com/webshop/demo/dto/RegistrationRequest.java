package com.webshop.demo.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class RegistrationRequest {

    @NotBlank
    private String sex;

    @NotBlank
    @Length(max = 100)
    private String firstName;

    @NotBlank
    @Length(max = 200)
    private String lastName;

    @NotBlank
    private String address;

    @NotBlank
    private String doornumber;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String city;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private MultipartFile profilePicture;

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoornumber() {
        return this.doornumber;
    }

    public void setDoornumber(String doornumber) {
        this.doornumber = doornumber;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegistrationRequest() {
    }

    public RegistrationRequest(String sex, String firstName, String lastName, String address, String doornumber,
            String postalCode, String city, String email, String username, String password,
            MultipartFile profilePicture) {
        this.sex = sex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.doornumber = doornumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
    }

}
