/*
package com.atdxt.Multi_logging.Entity;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
@Table(name = "customer2")
public class Customer2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    // Constructors, Getters, and Setters

    public Customer2() {
        // Default constructor
    }

    // Other constructors

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getPassword() {
        return password;
    }

   */
/* public String getPassword() {
        return decryptPassword(password);
    }
*//*

    public void setPassword(String password) {
        this.password = encryptPassword(password);
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

    // Utility methods for encryption and decryption

    private String encryptPassword(String password) {
        byte[] encodedBytes = Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    private String decryptPassword(String encryptedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

   */
/* public String getDecryptedPassword() {
        return decryptPassword(password);
    }*//*


}
*/


package com.atdxt.Multi_logging.Entity;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

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

    // Constructors, Getters, and Setters

    public Customer2() {
        // Default constructor
    }

    // Other constructors

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.encryptedPassword = encryptPassword(password);
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

    // Utility methods for encryption and decryption

    private String encryptPassword(String password) {
        byte[] encodedBytes = Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public String getDecryptedPassword() {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
