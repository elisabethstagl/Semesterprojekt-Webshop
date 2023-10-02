package com.webshop.demo.dto;

public class UserDTO {
    
    private String sex;

    
    private String firstName;

    
    private String lastName;

    private String address;

    private String doornumber;

    private String postalCode;

    private String city;

    private String email;

    private String username;

    private UserRole role;

    public enum UserRole {
        USER, ADMIN
    }

    public UserDTO() {
    }
    

    public UserDTO(String sex, String firstName, String lastName, String address, String doornumber, String postalCode, String city, String email, String username, UserRole role) {
        this.sex = sex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.doornumber = doornumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.username = username;
        this.role = role;
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

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
}

