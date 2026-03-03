package com.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnonymousConfessionPlatformApplication {

	public static void main(String[] args) {
		
		System.out.println(
			    java.util.Base64.getEncoder().encodeToString(
			        io.jsonwebtoken.security.Keys
			            .secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)
			            .getEncoded()
			    )
			);
		SpringApplication.run(AnonymousConfessionPlatformApplication.class, args);
	}

}
