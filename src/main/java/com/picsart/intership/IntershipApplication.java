package com.picsart.intership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class IntershipApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntershipApplication.class, args);
    }

}
