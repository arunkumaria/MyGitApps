package com.own.springboot_10best;

//import org.springframework.boot.SpringApplication;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class SpringbootToken1Application {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SpringbootToken1Application.class, args);
//	}
//
//}

//package com.example.toolkit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Bean;

import com.example.toolkit.bank.Account;
import com.example.toolkit.bank.AccountRepository;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
public class Springboot-10bestApplication
{

	public static void main(String[] args) { SpringApplication.run(Springboot-10bestApplication.class, args); }

	// Seed some bank accounts for transfer demo
	@Bean
	CommandLineRunner seed(AccountRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.save(new Account(null, "A001", 1000.0));
				repo.save(new Account(null, "A002", 500.0));
			}
		};
	}
}
