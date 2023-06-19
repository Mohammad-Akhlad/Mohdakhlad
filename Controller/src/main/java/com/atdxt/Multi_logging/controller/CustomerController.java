package com.atdxt.Multi_logging.controller;


import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/get")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/post")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
