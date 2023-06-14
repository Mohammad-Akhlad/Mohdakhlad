package com.atdxt.Jdbc_Jpa.Service;

import com.atdxt.Jdbc_Jpa.Entity.Customer;
import com.atdxt.Jdbc_Jpa.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public class DefaultCustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}   