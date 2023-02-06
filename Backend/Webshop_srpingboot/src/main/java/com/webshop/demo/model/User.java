package com.webshop.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Positive
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    // @NotBlank
    // @Column(name = "sex")
    // private String sex;

    @NotBlank
    @Column(name = "firstName")
    private String firstName;

    @NotBlank
    @Column(name = "lastName")
    private String lastName;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotBlank
    @Column(name = "doornumber")
    private String doornumber;

    @NotBlank
    @Column(name = "postalCode")
    private String postalCode;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    // CONSTRUCTOR

    public User() {
    }


    public User(String firstName, String lastName, String address, String doornumber, String postalCode, String city, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.doornumber = doornumber;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    

    // GETTERS & SETTERS

    public Long getId() {
        return this.id;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
