package com.atdxt.Jdbc_Jpa.Controller;


import com.atdxt.Jdbc_Jpa.Entity.Customer;
import com.atdxt.Jdbc_Jpa.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/get")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/post")
    public Customer saveCustomer(final @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
} 