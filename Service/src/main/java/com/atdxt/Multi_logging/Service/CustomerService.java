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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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

    public boolean isEmailExists(String email) {
        return customerRepository.existsByEmail(email);
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }

    public Customer getCustomerById(Long customerId) {
        // Fetch the customer by their ID from the database
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        // Check if the customer exists in the database
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            // Handle customer not found
            return null; // You can return null or throw an exception based on your requirements.
        }
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





    public Customer saveCustomer(String username, String password, Customer customer) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedOn(currentDateTime);
        customer.setLastModified(currentDateTime);





        // Get the existing Customer2 object associated with the customer
        Customer2 customer2 = customer.getCustomer2();

        // If no Customer2 object is associated, create a new one and associate it with the customer
        if (customer2 == null) {
            customer2 = new Customer2();
            customer2.setCustomer(customer);
            customer.setCustomer2(customer2);
        }

        // Set the provided username and encrypt the password
        customer2.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(password);
        customer2.setEncryptedPassword(encryptedPassword);



        Customer1 customer1 = customer.getCustomer1();
        if (customer1 != null) {
            customer1.setCreatedOn(currentDateTime);
            customer1.setLastModified(currentDateTime);
            customer1.setAge(customer.getCustomer1().getAge());
            customer1.setCustomer(customer);
        }

        // Update Customer1 if needed (you can add your logic here)

        // Save the changes to the database
        return customerRepository.save(customer);
    }

   /* public Customer saveCustomer(String username, String password, Customer customer) {
        // Other code...

        // Calculate the age based on the date of birth
        int age = calculateAge(customer.getDateOfBirth());

        // Get the existing Customer2 object associated with the customer
        Customer2 customer2 = customer.getCustomer2();

        // If no Customer2 object is associated, create a new one and associate it with the customer
        if (customer2 == null) {
            customer2 = new Customer2();
            customer2.setCustomer(customer);
            customer.setCustomer2(customer2);
        }

        // Set the provided username and encrypt the password
        customer2.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(password);
        customer2.setEncryptedPassword(encryptedPassword);

        // Set the age in the Customer1 object
        customer.getCustomer1().setAge(age);

        // Save the changes to the database
        return customerRepository.save(customer);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
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



    public void saveCustomer(Customer customer) {
    }
}
