package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer1;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import com.atdxt.Multi_logging.Repository.Customer1Repository;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import javax.persistence.EntityNotFoundException;
//import javax.transaction.Transactional;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Customer2 getUserByUsername(String username) {
        System.out.println("Getting user details for username: " + username);

        Optional<Customer2> userOptional = customer2Repository.findByUsername(username);

        if (userOptional.isPresent()) {
            Customer2 customer2 = userOptional.get();
            System.out.println("User details retrieved: " + customer2);
            return customer2;
        } else {
            System.out.println("User not found for username: " + username);
            return null;
        }
    }

//    @Transactional
    public Customer saveCustomer(String username, String password, Customer customer) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedOn(currentDateTime);
        customer.setLastModified(currentDateTime);

        // Create and set the Customer2 object for provided login
        Customer2 customer2 = new Customer2();
        customer2.setUsername(username); // Set the provided username
        customer2.setEncryptedPassword(password); // Encode the provided password
//        customer2.encryptPassword();
        customer2.setCreatedOn(currentDateTime);
        customer2.setLastModified(currentDateTime);
        customer2.setCustomer(customer);
        customer.setCustomer2(customer2); // Associate Customer2 with Customer

        // You can also handle the individual login example here if needed

        // Code for Customer1 association (if applicable) remains unchanged
        Customer1 customer1 = customer.getCustomer1();
        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime);
            customer1.setLastModified(currentDateTime);
            customer1.setAge(customer.getCustomer1().getAge());
            customer1.setCustomer(customer);
        }

        return customerRepository.save(customer);
    }

//
//    public User getUserByUsername(String username) {
//        System.out.println("Getting user details for username: " + username);
//
//        Optional<User> userOptional = userRepository1.findByUsername(username);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            System.out.println("User details retrieved: " + user);
//            return user;
//        } else {
//            System.out.println("User not found for username: " + username);
//            return null;
//        }
//    }
// In CustomerService



   /* public Customer2 getCustomerByUsername(String username, String password) {
        System.out.println("Getting user details for username: " + username);

        Customer2 customer2 = customer2Repository.findByUsername(username).orElse(null);

        if (customer2 != null && customer2.isPasswordMatch(password)) {
            System.out.println("User details retrieved: " + customer2);
            return customer2;
        } else {
            System.out.println("User not found or invalid credentials for username: " + username);
            return null;
        }
    }
*/

    public Customer2 getCustomerByUsername(String username) {
        System.out.println("Getting user details for username: " + username);

        Customer2 customer2 = customer2Repository.findByUsername(username)
                .orElse(null);
        if (customer2 != null) {
            return customer2;
        }
        return null;
    }



//
//    public Customer updateCustomer(Long id, Customer updatedCustomer) {
//        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);
//
//        if (existingCustomerOptional.isPresent()) {
//            Customer existingCustomer = existingCustomerOptional.get();
//            existingCustomer.setName(updatedCustomer.getName());
//            existingCustomer.setCity(updatedCustomer.getCity());
//
//            if (updatedCustomer.getPhoneNumber() != null) {
//                existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
//            }
//
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            existingCustomer.setLastModified(currentDateTime);
//
//            Customer1 existingCustomer1 = existingCustomer.getCustomer1();
//            Customer1 updatedCustomer1 = updatedCustomer.getCustomer1();
//            if (existingCustomer1 != null && updatedCustomer1 != null) {
//                existingCustomer1.setAge(updatedCustomer1.getAge());
//                existingCustomer1.setLastModified(currentDateTime);
//            }
//
//            customerRepository.save(existingCustomer);
//            return existingCustomer;
//        } else {
//            throw new EntityNotFoundException("Customer not found with id: " + id);
//        }
//    }

    // Getting customer2 values in customer
  /*  public List<Customer> getAllCustomersWithCustomer2() {
        return customerRepository.findAllWithCustomer2();
    }*/
}
