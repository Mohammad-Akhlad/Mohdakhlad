package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService1 implements UserDetailsService {

    private final Customer2Repository customer2Repository;

    @Autowired
    public UserService1(Customer2Repository customer2Repository) {
        this.customer2Repository = customer2Repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer2 userEncrypt = customer2Repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String password = userEncrypt.getPassword();
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(password)
                .roles("ADMIN")
                .build();
    }

}