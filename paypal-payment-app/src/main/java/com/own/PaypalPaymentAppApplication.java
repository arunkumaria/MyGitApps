package com.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@SpringBootApplication
@SpringBootApplication
@EntityScan(basePackages = "com.own")
public class PaypalPaymentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalPaymentAppApplication.class, args);
	}

}
