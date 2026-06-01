package com.own;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.own.client")
public class BffServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                BffServiceApplication.class,
                args
        );
    }
}