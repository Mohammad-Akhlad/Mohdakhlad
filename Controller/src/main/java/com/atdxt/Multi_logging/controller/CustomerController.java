package com.atdxt.Multi_logging.controller;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import com.atdxt.Multi_logging.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    private CustomerService customerService;

    @GetMapping("/get")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/exception")
    public void createRuntimeException() {
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isPresent()) {
            return ResponseEntity.ok(optionalCustomer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @PostMapping("/post")
//    public ResponseEntity<String> saveUser(@RequestBody Customer customer) {
//        boolean phoneExists = customerService.isPhoneNumberExists(customer.getPhoneNumber());
//        if (phoneExists) {
//            return ResponseEntity.badRequest().body("Invalid user: Phone number already exists");
//        }
//        if (!customerService.isValidPhoneNo(customer.getPhoneNumber())) {
//            return ResponseEntity.badRequest().body("Invalid user: Invalid phone number format");
//        }
//
//        logger.info("saveCustomer");
//        customerService.saveCustomer(customer);
//        return ResponseEntity.ok("User saved successfully");
//    }
@PostMapping("/post")
public ResponseEntity<String> saveUser(@RequestBody Customer customer) {
    if (customer.getPhoneNumber() != null) {
        boolean phoneExists = customerService.isPhoneNumberExists(customer.getPhoneNumber());
        if (phoneExists) {
            return ResponseEntity.badRequest().body("Invalid user: Phone number already exists");
        }
        if (!customerService.isValidPhoneNo(customer.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Invalid user: Invalid phone number format");
        }
    }

    logger.info("saveCustomer");
    customerService.saveCustomer(customer);
    return ResponseEntity.ok("User saved successfully");
}


   /* @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer updatedCustomer) {
        try {
            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }*/


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer updatedCustomer) {
        try {
            if (updatedCustomer.getPhoneNumber() != null) {
                boolean phoneExists = customerService.isPhoneNumberExists(updatedCustomer.getPhoneNumber());
                if (phoneExists) {
                    return ResponseEntity.badRequest().body("Invalid user: Phone number already exists");
                }
                if (!customerService.isValidPhoneNo(updatedCustomer.getPhoneNumber())) {
                    return ResponseEntity.badRequest().body("Invalid user: Invalid phone number format");
                }
            }

            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok("User updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @Autowired
    private Customer2Repository customer2Repository;

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
        return customer2Repository.save(customer2);
    }
}
