package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer;
import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import com.atdxt.Multi_logging.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Random;

@Service
public class UserService implements EmailService {

    private final CustomerRepository customerRepository;
    private final JavaMailSender javaMailSender;

    private Customer2Repository customer2Repository;

    @Autowired
    public void CustomerService(Customer2Repository customer2Repository) {
        this.customer2Repository = customer2Repository;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(CustomerRepository customerRepository, JavaMailSender javaMailSender, Customer2Repository customer2Repository) {
        this.customerRepository = customerRepository;
        this.javaMailSender = javaMailSender;
        this.customer2Repository = customer2Repository;
    }

    // Other methods in UserService
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }



    public void updatePassword(String email, String newPassword) {
        // Find the customer by email
        Customer customer = findByEmail(email);
        if (customer == null) {
            throw new UserNotFoundException("User with email: " + email + " not found.");
        }

        // Get the associated Customer2 entity from Customer
        Customer2 customer2 = customer.getCustomer2();
        if (customer2 == null) {
            // If there is no associated Customer2, handle the scenario based on your application's requirements
            throw new RuntimeException("Customer2 entity not found for the user with email: " + email);
        }

        // Update the user's password in the Customer2 entity
        String encryptedPassword = passwordEncoder.encode(newPassword);
        customer2.setEncryptedPassword(encryptedPassword);

        // Save the changes to the database
        customer2Repository.save(customer2);
    }


    @Override
    public void sendOtpEmail(String email, String otp) {
        // Validate the email format using a regular expression
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new InvalidEmailException("Invalid email format.");
        }

        // Validate OTP (you can add additional checks if needed)
        if (otp.length() != 6 || !otp.matches("[0-9]+")) {
            throw new InvalidOtpException("Invalid OTP format. OTP must be a 6-digit number.");
        }

        // Send the email with the OTP to the user's email address
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + otp);

        javaMailSender.send(message);

        // Store the OTP in the session
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("otp_" + email, otp);
    }




    public String generateOtp() {
        // Implement the logic to generate the OTP here
        // For example, you can use a random number generator or a third-party library to generate the OTP
        // For simplicity, let's assume we are generating a 6-digit OTP
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generates a 6-digit OTP
        return String.valueOf(otpValue);
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public class InvalidEmailException extends RuntimeException {
        public InvalidEmailException(String message) {
            super(message);
        }
    }

    public class InvalidOtpException extends RuntimeException {
        public InvalidOtpException(String message) {
            super(message);
        }
    }


}
