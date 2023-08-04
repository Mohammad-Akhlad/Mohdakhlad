package com.atdxt.Multi_logging.Entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "customer2")
//public class Customer2 {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "username")
//    private String username;
//
//    @Column(name = "password")
//    private String encryptedPassword;
//
//    @Column(name = "created_on")
//    private LocalDateTime createdOn;
//
//    @Column(name = "last_modified")
//    private LocalDateTime lastModified;
//
//    @OneToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
//
//
//
//    public Customer2() {
//    }
//
//
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return encryptedpassword;
//    }
//
//    public void setPassword(String password) {
//        this.encryptedpassword = password;
//    }
//    public void encryptPassword() {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        this.encryptedpassword = passwordEncoder.encode(this.encryptedpassword);
//
//    }
//
//    public LocalDateTime getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(LocalDateTime createdOn) {
//        this.createdOn = createdOn;
//    }
//
//    public LocalDateTime getLastModified() {
//        return lastModified;
//    }
//
//    public void setLastModified(LocalDateTime lastModified) {
//        this.lastModified = lastModified;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//   /* // Method to check if the raw password matches the encoded password
//    public boolean isPasswordMatch(String rawPassword) {
//        return passwordEncoder.matches(rawPassword, encryptedPassword);
//    }*/
//}
@Entity
@Table(name = "customer2")
public class Customer2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "encrypted_password")
    private String encryptedPassword; // Rename 'password' to 'encryptedPassword'

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

   /* private String otp;*/

    public Customer2() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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

   /* public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }*/

    // Method to encrypt the raw password using BCryptPasswordEncoder
    public void encryptPassword(String rawPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.encryptedPassword = passwordEncoder.encode(rawPassword);
    }

    // Method to check if the raw password matches the encrypted password
    public boolean isPasswordMatch(String rawPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}
