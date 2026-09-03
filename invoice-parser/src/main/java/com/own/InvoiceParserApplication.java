package com.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class InvoiceParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceParserApplication.class, args);
	}

	@PostConstruct
	public void checkJava() {
		System.out.println("====================================");
		System.out.println("JNA PATH = " + System.getProperty("jna.library.path"));
		System.out.println("JAVA HOME: " + System.getProperty("java.home"));
		System.out.println("OS ARCH  : " + System.getProperty("os.arch"));
		System.out.println("====================================");
	}

}
