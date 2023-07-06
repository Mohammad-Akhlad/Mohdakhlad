package com.atdxt.Multi_logging.controller;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer1;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import com.atdxt.Multi_logging.Repository.Customer1Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    static {
        System.setProperty("log4j2.debug", "true");
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Customer1Repository customer1Repository;

    @Autowired
    private Customer2Repository customer2Repository;

    @GetMapping("/get")
    public List<Customer> getCustomers() {

        return customerRepository.findAll();
    }
    @GetMapping("/exception")
    public void createRuntimeException() {
        try {
            int result = 10 / 0; // Division by zero to trigger the runtime exception
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            return ResponseEntity.ok(optionalCustomer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/post")
    public Customer saveCustomer(@RequestBody Customer customer) {
        Customer1 customer1 = customer.getCustomer1();
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time

        customer.setCreatedOn(currentDateTime); // Set createdOn
        customer.setLastModified(currentDateTime); // Set lastModified

        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime); // Set createdOn
            customer1.setLastModified(currentDateTime); // Set lastModified
            customer1.setCustomer(customer); // Set the customer for the customer1 entity
        }

        customerRepository.save(customer); // Save the customer entity

        if (customer1 != null) {
            customer1Repository.save(customer1); // Save the customer1 entity
        }

        return customer;
    }


    @GetMapping("/get2")
    public List<Customer2> getCustomers2() {
        return customer2Repository.findAll();
    }

    @GetMapping("/get2/{id}")
    public ResponseEntity<Customer2> getCustomer2ById(@PathVariable Long id) {
        Optional<Customer2> optionalCustomer2 = customer2Repository.findById(id);
        if (optionalCustomer2.isPresent()) {
            return ResponseEntity.ok(optionalCustomer2.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post2")
    public Customer2 saveCustomer2(@RequestBody Customer2 customer2) {
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time

        customer2.setCreatedOn(currentDateTime); // Set createdOn
        customer2.setLastModified(currentDateTime); // Set lastModified

        customer2Repository.save(customer2); // Save the customer2 entity

        return customer2;
    }


@PutMapping("/update/{id}")
public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer updatedCustomer) {
    Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

    if (existingCustomerOptional.isPresent()) {
        Customer existingCustomer = existingCustomerOptional.get();
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setCity(updatedCustomer.getCity());

        LocalDateTime currentDateTime = LocalDateTime.now();
        existingCustomer.setLastModified(currentDateTime);

        // Update the customer1 if present
        Customer1 existingCustomer1 = existingCustomer.getCustomer1();
        Customer1 updatedCustomer1 = updatedCustomer.getCustomer1();
        if (existingCustomer1 != null && updatedCustomer1 != null) {
            existingCustomer1.setAge(updatedCustomer1.getAge());
            existingCustomer1.setLastModified(currentDateTime);
        }

        return customerRepository.save(existingCustomer);
    } else {
        throw new EntityNotFoundException("Customer not found with ID: " + id);
    }
}

}
