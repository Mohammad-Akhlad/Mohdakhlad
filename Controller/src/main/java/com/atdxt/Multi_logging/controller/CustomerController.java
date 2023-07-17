package com.atdxt.Multi_logging.controller;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer1;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import com.atdxt.Multi_logging.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    static {
        System.setProperty("log4j2.debug", "true");
    }

    @Autowired
    private CustomerService customerService;

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

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView("login");
        if (logout != null) {
            modelAndView.addObject("logoutMessage", "You have been logged out successfully.");
        }
        return modelAndView;
    }


    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/favicon.ico")
    @ResponseBody
    public void handleFaviconRequest() {
        // Do nothing or return a favicon.ico file
    }


    @GetMapping("/get")
    public ModelAndView getAllCustomers() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("customers");
        mav.addObject("customers", customerRepository.findAll());
        return mav;
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
    public String getCustomerById(@PathVariable Long id, Model model) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            List<Customer> customerList = new ArrayList<>();
            customerList.add(customer);
            model.addAttribute("customers", customerList);
            return "customers";
        } else {
            return "error";
        }
    }





  /*  @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute("customer") Customer customer) {
        // Save the customer to the table using the customerService
        customerService.saveCustomer(customer);

        ModelAndView modelAndView = new ModelAndView("redirect:/get"); // Redirect to the customer list page or any other desired page
        return modelAndView;
    }
*/
  @PostMapping("/signup")
  public ModelAndView signup(@ModelAttribute("customer") @DateTimeFormat(pattern = "dd-MM-yyyy") Customer customer) {
      // Save the customer to the table using the customerService
      customerService.saveCustomer(customer);

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
        customerService.saveCustomer(customer);
        return ResponseEntity.ok("User saved successfully");
    }

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
