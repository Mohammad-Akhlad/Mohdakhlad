package com.atdxt.Multi_logging.Entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer2")
public class Customer2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String encryptedPassword;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;


    @OneToOne(mappedBy = "customer2")
    private Customer customer;


    // Non-static field to store the BCryptPasswordEncoder
    @Transient
    private final BCryptPasswordEncoder passwordEncoder;

    // Transient field to store the raw password before encoding

    public Customer2() {
        // Initialize the passwordEncoder field in the constructor
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Other constructors, getters, and setters

    // Getters and setters for the fields

    // ...

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return encryptedPassword;
    }

    public void setPassword(String password) {
        this.encryptedPassword = passwordEncoder.encode(password);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

   /* // Method to check if the raw password matches the encoded password
    public boolean isPasswordMatch(String rawPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }*/
}