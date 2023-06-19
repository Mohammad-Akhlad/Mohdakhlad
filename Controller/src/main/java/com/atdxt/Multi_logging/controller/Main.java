package com.atdxt.Multi_logging.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.atdxt.*"})
@ComponentScan(basePackages = {"com.atdxt.*"})
@EnableJpaRepositories(basePackages = {"com.atdxt.*"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

