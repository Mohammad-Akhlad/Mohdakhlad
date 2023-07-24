
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

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Home");
        mav.addObject("message", "Welcome to the home page");
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView showSignupForm() {
        ModelAndView modelAndView = new ModelAndView("Signup");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

/*
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (logout != null) {
            modelAndView.addObject("logoutMessage", "You have been logged out successfully.");
        }
        return modelAndView;
    }
*/






   @GetMapping("/get")
    public ModelAndView getAllCustomers(ModelAndView model, Principal principal) {
        if (principal == null) {
            // The user is not logged in.
            return new ModelAndView("redirect:/login");
        }

        String username = principal.getName();

        if (username.equals("admin")) {
            List<Customer> customers = customerService.getCustomers();
            model.setViewName("customers");
            model.addObject("customers", customers);
            return model;
        } else {
            Customer2 customer = customerService.getCustomerByUsername(username);
            if (customer != null) {
                List<Customer> customers = new ArrayList<>();
                customers.add(customer.getCustomer());
                model.setViewName("customers");
                model.addObject("customers", customers);
                return model;
            } else {
                return new ModelAndView("redirect:/signup");
            }
        }
    }


    @GetMapping("/exception")
    public void createRuntimeException() {
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
    }

//    @GetMapping("/get/{id}")
//    public ModelAndView getCustomerById(@PathVariable Long id) {
//        ModelAndView mav = new ModelAndView();
//
//        // Assuming customerService provides a method to retrieve a customer by ID
//        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
//
//        if (optionalCustomer.isPresent()) {
//            Customer customer = optionalCustomer.get();
//            mav.setViewName("customers"); // Assuming "customer_details" is the template for displaying an individual customer's details.
//            mav.addObject("customer", customer);
//        } else {
//            // Handle the case when the customer is not found with the given ID
//            mav.setViewName("customer_not_found");
//        }
//
//        return mav;
//    }

    @Autowired
    private Customer2Repository customer2Repository;

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute("customer") Customer customer,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("dateOfBirth") String dateOfBirth) {

        // Convert the dateOfBirth string to LocalDate using a custom DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDateOfBirth = LocalDate.parse(dateOfBirth, formatter);

        // Set the parsed dateOfBirth in the Customer object
        customer.setDateOfBirth(parsedDateOfBirth);

        // Create a new Customer2 object and set the username
        Customer2 customer2 = new Customer2();
        customer2.setUsername(username);

        // Encode the password using the passwordEncoder and set it in the Customer2 object
        String encodedPassword = passwordEncoder.encode(password);
        customer2.setEncryptedPassword(encodedPassword);

        customer2.setCreatedOn(LocalDateTime.now());
        customer2.setLastModified(LocalDateTime.now());

        // Associate the Customer2 object with the Customer object
        customer.setCustomer2(customer2);

        // Save the customer to the table using the customerService
        customerService.saveCustomer(username, encodedPassword, customer);

        ModelAndView modelAndView = new ModelAndView("redirect:/get"); // Redirect to the customer list page or any other desired page
        return modelAndView;
    }








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
        // Call the saveCustomer method in the service layer with the necessary arguments
        String username = "your_username";
        String password = "your_password";
        customerService.saveCustomer(username, password, customer);
        return ResponseEntity.ok("User saved successfully");
    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer updatedCustomer) {
//        try {
//            if (updatedCustomer.getPhoneNumber() != null) {
//                boolean phoneExists = customerService.isPhoneNumberExists(updatedCustomer.getPhoneNumber());
//                if (phoneExists) {
//                    return ResponseEntity.badRequest().body("Invalid user: Phone number already exists");
//                }
//                if (!customerService.isValidPhoneNo(updatedCustomer.getPhoneNumber())) {
//                    return ResponseEntity.badRequest().body("Invalid user: Invalid phone number format");
//                }
//            }
//
//            Customer customer = customerService.updateCustomer(id, updatedCustomer);
//            return ResponseEntity.ok("User updated successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


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

