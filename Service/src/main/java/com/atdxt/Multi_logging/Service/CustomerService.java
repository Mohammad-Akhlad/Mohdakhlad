
package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer1;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import com.atdxt.Multi_logging.Repository.Customer1Repository;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
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

    @Autowired
    private Customer2Repository customer2Repository;

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
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedOn(currentDateTime);
        customer.setLastModified(currentDateTime);
        Customer2 customer2 = new Customer2();
        customer2.setCreatedOn(currentDateTime);
        customer2.setLastModified(currentDateTime);
        customer2.setUsername(customer.getCustomer2().getUsername());
        customer2.setPassword(customer.getCustomer2().getPassword());
        customer2.setCustomer(customer);
        customer.setCustomer2(customer2); // Associate Customer2 with Customer


//        if (customer.getDateOfBirth() != null) {
//            customer.setDateOfBirth(customer.getDateOfBirth());
//        }

        Customer1 customer1 = customer.getCustomer1();
        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime);
            customer1.setLastModified(currentDateTime);
            customer1.setAge(customer.getCustomer1().getAge());
            customer1.setCustomer(customer);

        }
        return customerRepository.save(customer);
    }

  /*  public Customer saveCustomer(Customer customer) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedOn(currentDateTime);
        customer.setLastModified(currentDateTime);

        // Get the Customer1 and Customer2 objects from the provided Customer entity
        Customer1 customer1 = customer.getCustomer1();
        Customer2 customer2 = customer.getCustomer2();

        // Set the createdOn and lastModified for Customer1 and Customer2, if available
        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime);
            customer1.setLastModified(currentDateTime);
        }

        if (customer2 != null) {
            customer2.setCreatedOn(currentDateTime);
            customer2.setLastModified(currentDateTime);
        }

        // Set the relationship between Customer and Customer2
        if (customer2 != null) {
            customer2.setCustomer(customer);
        }

        // Save Customer and related entities to the database
        return customerRepository.save(customer);
    }*/



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


    //getting customer2 values in customer
    public List<Customer> getAllCustomersWithCustomer2() {
        return customerRepository.findAllWithCustomer2();
    }
}


