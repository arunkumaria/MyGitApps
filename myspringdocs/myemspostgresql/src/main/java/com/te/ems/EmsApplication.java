package com.te.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EmsApplication {
	
public static void main(String[] args) {
		System.out.println("ems start");
		
		SpringApplication.run(EmsApplication.class, args);
		System.out.println("ems end");
	}

}
