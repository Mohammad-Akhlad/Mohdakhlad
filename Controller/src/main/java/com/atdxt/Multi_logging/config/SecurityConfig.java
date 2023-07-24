package com.atdxt.Multi_logging.config;
import com.atdxt.Multi_logging.Service.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .authorizeRequests(authorize ->authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/signup").permitAll()
                      /*  .requestMatchers("/get").hasRole("ADMIN")*/

                        .anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/get") // Specify the URL to redirect after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Set the logout success URL to the home page
                        .permitAll()
                );


        return  http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}









