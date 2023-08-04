package com.atdxt.Multi_logging.Service;

import com.atdxt.Multi_logging.Entity.Customer2;
import com.atdxt.Multi_logging.Repository.Customer2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService1 implements UserDetailsService {

    @Autowired
    private Customer2Repository customer2Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user details from the database based on the username
        Customer2 customer2 = customer2Repository.findByUsername(username).orElse(null);

        if (customer2 == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Create a list of authorities (roles) for the user (you can customize this based on your application)
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Create a UserDetails object with the retrieved user details and authorities
        UserDetails userDetails = new User(
                customer2.getUsername(),
                customer2.getEncryptedPassword(),
                authorities
        );

        return userDetails;
    }
}

