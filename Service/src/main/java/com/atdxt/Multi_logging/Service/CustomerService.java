package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer1;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import com.atdxt.Multi_logging.Repository.Customer1Repository;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Customer1Repository customer1Repository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }


    private static final String PHONE_NUMBER_REGEX = "\\d{10}"; // Assuming a valid phone number consists of 10 digits

    public boolean isValidPhoneNo(String phoneNo) {
        RegexValidator regexValidator = new RegexValidator(PHONE_NUMBER_REGEX);
        return regexValidator.isValid(phoneNo);
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }

    public Customer saveCustomer(Customer customer) {
        Customer1 customer1 = customer.getCustomer1();
        LocalDateTime currentDateTime = LocalDateTime.now();

        customer.setCreatedOn(currentDateTime);
        customer.setLastModified(currentDateTime);

        if (customer.getDateOfBirth() != null) {
            customer.setDateOfBirth(customer.getDateOfBirth());
        }
//        if (customer.getPhoneNumber() != null) {
//            customer.setPhoneNumber(customer.getPhoneNumber());
//        }

        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime);
            customer1.setLastModified(currentDateTime);
            customer1.setCustomer(customer);
        }

        customerRepository.save(customer);

        if (customer1 != null) {
            customer1Repository.save(customer1);
        }

        return customer;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setCity(updatedCustomer.getCity());

            if (updatedCustomer.getPhoneNumber() != null) {
                existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            existingCustomer.setLastModified(currentDateTime);

            Customer1 existingCustomer1 = existingCustomer.getCustomer1();
            Customer1 updatedCustomer1 = updatedCustomer.getCustomer1();
            if (existingCustomer1 != null && updatedCustomer1 != null) {
                existingCustomer1.setAge(updatedCustomer1.getAge());
                existingCustomer1.setLastModified(currentDateTime);
            }

            customerRepository.save(existingCustomer);
            return existingCustomer;
        } else {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
    }
}


