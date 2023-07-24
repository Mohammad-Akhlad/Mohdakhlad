package com.atdxt.Multi_logging.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.atdxt.*"})
@ComponentScan(basePackages = {"com.atdxt.Multi_logging"}) // Specify the root package containing all components
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
