
package com.atdxt.Multi_logging.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import com.atdxt.Multi_logging.Service.CustomerService;
import com.atdxt.Multi_logging.Service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    private final UserService userService;

    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Home");
        mav.addObject("message", "Welcome to the home page");
        return mav;
    }


        @GetMapping("/login")
        public ModelAndView customLogin() {
            ModelAndView modelAndView = new ModelAndView("login");
/*
            modelAndView.addObject("customer", new Customer2());
*/
            return modelAndView;
        }

  /*  @PostMapping("/login") // Change @GetMapping to @PostMapping
    public ModelAndView processLogin(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        // Handle the login logic here (authenticate the user, redirect to success page, etc.)
        // For simplicity, let's assume you have a method to handle the login logic and redirect to a success page

        // Example:
        boolean authenticated = authenticateUser(username, password); // Implement your authentication logic here
        if (authenticated) {
            // Redirect to a success page after successful login
            return new ModelAndView("redirect:/success"); // Change "/success" to the desired landing page URL
        } else {
            // If login fails, return back to the login page with an error message
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid credentials. Please try again.");
            return modelAndView;
        }
    }

    private boolean authenticateUser(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }*/

    @PostMapping("/login") // Change @GetMapping to @PostMapping
    public ModelAndView processLogin(@RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        // Fetch the Customer2 object for authentication
        Customer2 customer2 = customerService.getUserByUsername(username);

        if (customer2 != null && passwordEncoder.matches(password, customer2.getEncryptedPassword())) {
            // Authentication successful, redirect to a success page
            return new ModelAndView("redirect:/success"); // Change "/success" to the desired landing page URL
        } else {
            // If login fails, return back to the login page with an error message
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid credentials. Please try again.");
            return modelAndView;
        }
    }



    @GetMapping("/signup")
    public ModelAndView showSignupForm() {
        ModelAndView modelAndView = new ModelAndView("Signup");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }




    @GetMapping("/forgetPassword")
    public ModelAndView showForgetPasswordForm() {
        ModelAndView modelAndView = new ModelAndView("email_verification");
        return modelAndView;
    }



    @PostMapping("/verifyEmail")
    public ModelAndView verifyEmail(@RequestParam("email") String email) {
        // Implement email verification logic here and send the OTP to the user's email
        Customer customer = userService.findByEmail(email);
        if (customer == null) {
            ModelAndView modelAndView = new ModelAndView("email_verification");
            modelAndView.addObject("error", "Invalid email. Please enter the email you used for sign-up.");
            return modelAndView;
        }

        // Generate and send OTP to the user's email
        String otp = userService.generateOtp();
        userService.sendOtpEmail(email, otp);

        ModelAndView modelAndView = new ModelAndView("otp_page");
        modelAndView.addObject("email", email); // Pass the email to the OTP verification page
        return modelAndView;
    }



    @PostMapping("/verifyOtp")
    public ModelAndView verifyOTP(@RequestParam("email") String email,
                                  @RequestParam("otp") String otp,
                                  HttpSession session) {

        // Retrieve the stored OTP from the user's session
        String storedOtp = (String) session.getAttribute("otp_" + email);

        System.out.println("Entered OTP: " + otp);
        System.out.println("Stored OTP: " + storedOtp);

        // Check if the entered OTP matches the stored OTP
        if (!otp.equals(storedOtp)) {
            ModelAndView modelAndView = new ModelAndView("otp_page");
            modelAndView.addObject("email", email);
            modelAndView.addObject("error", "Invalid OTP. Please try again.");
            return modelAndView;
        }

        // If OTP is valid, remove the OTP from the session and redirect to the change_password.html page
        session.removeAttribute("otp_" + email);

        ModelAndView modelAndView = new ModelAndView("change_password");
        modelAndView.addObject("email", email);
        return modelAndView;
    }


    @PostMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword) {
        System.out.println("Change password request");

        // Check if the passwords match
        if (!password.equals(confirmPassword)) {
            ModelAndView modelAndView = new ModelAndView("change_password");
            modelAndView.addObject("email", email);
            modelAndView.addObject("error", "Passwords do not match. Please try again.");
            return modelAndView;
        }

        // Find the user/customer by email
        Customer customer = userService.findByEmail(email);
        if (customer == null) {
            // Handle invalid email (optional)
            ModelAndView modelAndView = new ModelAndView("change_password");
            modelAndView.addObject("error", "Invalid email. Please try again.");
            return modelAndView;
        }

        try {
            // Update the user's password
            userService.updatePassword(email, password);
        } catch (UserService.UserNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("change_password");
            modelAndView.addObject("email", email);
            modelAndView.addObject("error", "User not found. Please try again.");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("success", "Password changed successfully. You can now log in with your new password.");
        return modelAndView;
    }






    @GetMapping("/get")
    public ModelAndView getAllCustomers(ModelAndView model, Principal principal) {
        if (principal == null) {
            // The user is not logged in.
            return new ModelAndView("redirect:/Login");
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



    @GetMapping("/get/{id}")
    public ModelAndView getCustomerById(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();

        // Assuming customerService provides a method to retrieve a customer by ID
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerService.getCustomerById(id));

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            mav.setViewName("customers"); // Assuming "customer_details" is the template for displaying an individual customer's details.
            mav.addObject("customer", customer);
        } else {
            // Handle the case when the customer is not found with the given ID
            mav.setViewName("customer_not_found");
        }

        return mav;
    }

    @Autowired
    private Customer2Repository customer2Repository;



    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute("customer") Customer customer,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("dateOfBirth") String dateOfBirth,
                               @RequestParam("email") String email,
                               @RequestParam("imageFile") MultipartFile imageFile) {

        // Convert the dateOfBirth string to LocalDate using a custom DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDateOfBirth = LocalDate.parse(dateOfBirth, formatter);

        // Set the parsed dateOfBirth in the Customer object
        customer.setDateOfBirth(parsedDateOfBirth);
        customer.setEmail(email);

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
       /* customerService.saveCustomer(username, encodedPassword, customer);*/

        try {
            // Upload the image to S3 bucket if an image file is provided
            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                String imageUrl = uploadImageToS3(fileName, imageFile); // Method to upload the image to S3
                customer.setImageUrl(imageUrl); // Set the image URL in the Customer object
            }
        } catch (Exception e) {
            // Handle the exception (e.g., return an error response)
            e.printStackTrace();
            return new ModelAndView("redirect:/error"); // Redirect to an error page on image upload failure
        }

        // Save the updated customer (with image URL) to the database
        customerService.saveCustomer(username, encodedPassword, customer);

        ModelAndView modelAndView = new ModelAndView("redirect:/get"); // Redirect to the customer list page or any other desired page
        return modelAndView;
    }
    private String uploadImageToS3(String fileName, MultipartFile imageFile) {
        try {
            // Upload the image to S3 bucket
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, imageFile.getInputStream(), null));

            // Get the URL of the uploaded image
            String imageUrl = amazonS3.getUrl(bucketName, fileName).toString();

            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException("Image upload to S3 failed", e);
        }
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

