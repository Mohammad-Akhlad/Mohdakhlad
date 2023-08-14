package com.atdxt.Multi_logging.config;
import com.atdxt.Multi_logging.Service.EmailService;
import com.atdxt.Multi_logging.Service.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserService1 userDetailsService;


    @Autowired
    @Lazy
    public SecurityConfig(UserService1 userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;

    }
//
//    @Bean
//    public org.springframework.web.filter.CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:8081"); // Replace with your Flutter port
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        return new CorsFilter(source);
//    }

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:8081"); // Replace with your Flutter port
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        CorsFilter corsFilter = new CorsFilter(source);
//
//        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set highest precedence
//
//        return filterRegistrationBean;
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize ->authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers( "/login").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/SignupPage").permitAll()
                        .requestMatchers("/forgetPassword").permitAll()
                        .requestMatchers("/verifyEmail").permitAll()
                        .requestMatchers("/verifyOtp").permitAll()
                        .requestMatchers("/changePassword").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/get")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Set the logout success URL to the home page
                        .permitAll()
                );


        return  http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public EmailService emailService() {
        return new EmailService() {
            @Override
            public void sendOtpEmail(String email, String otp) {
                // Replace this with your actual email sending logic
                // For example, using JavaMail or any email sending service
                // Example: Send an email with the OTP to the user's email address
                System.out.println("Sending OTP to email: " + email);
                System.out.println("OTP: " + otp);
                // Implement the logic to send an email with the OTP here
            }
        };
    }


}









