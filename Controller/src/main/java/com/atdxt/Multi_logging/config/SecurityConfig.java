
package com.atdxt.Multi_logging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;






@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http
                .authorizeRequests(authorize -> authorize
                .antMatchers("/").permitAll() // Allow access to the home page
                .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/get") // Specify the URL to redirect after successful login
                        .permitAll()

                );*/


            http
                    .authorizeRequests()
                    .antMatchers("/", "/signup").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/get")
                    .permitAll();
        }





    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("Akhlad123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




