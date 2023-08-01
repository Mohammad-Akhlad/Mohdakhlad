
package com.atdxt.Multi_logging.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
@Table(name = "customer")
@JsonIgnoreProperties(ignoreUnknown = true)
/*@EntityListeners(CustomerEntityListener.class)*/
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



  @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
  private Customer1 customer1;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private Customer2 customer2;

    private String email;



    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "Date_of_Birth")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Column(name = "Phone_no")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;

    private String username;

    private String password;

  /*private String username; // Add the username field

    private String password;*/ // Add the password field

    /*@OneToOne(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("customer")
    private Customer1 customer1;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("customer")
    private Customer2 customer2;*/


    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    // Constructors, Getters, and Setters

    public Customer() {
        // Default constructor
    }

    // Other constructors

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customer1 getCustomer1() {
        return customer1;
    }

    public void setCustomer1(Customer1 customer1) {
        this.customer1 = customer1;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer2 getCustomer2() {  return customer2;  }

   // public void setCustomer2(Customer2 customer2) { this.customer2 = customer2; }

    public void setCustomer2(Customer2 customer2) {
        this.customer2 = customer2;
        if (customer2 != null) {
            customer2.setCustomer(this);
        }

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

    public void setPassword(String password) {
        this.password = password;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
