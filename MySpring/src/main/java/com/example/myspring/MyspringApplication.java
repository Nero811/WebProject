package com.example.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.myspring.entity")
public class MyspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspringApplication.class, args);
    }

}
