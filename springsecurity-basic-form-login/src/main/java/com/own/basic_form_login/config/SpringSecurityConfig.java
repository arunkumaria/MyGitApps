package com.own.basic_form_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withUsername("user").password("{noop}pass") // {noop} indicates plain text password (for
																			// demo purposes)
				.roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll() // Allow access to the login page
				.anyRequest().authenticated() // Secure all other endpoints
		).formLogin(form -> form.loginPage("/login") // Use the custom login page
				.defaultSuccessUrl("/welcome", true) // Redirect to welcome page after login
		).logout(logout -> logout.logoutSuccessUrl("/login") // Redirect to login page after logout
				.permitAll());

		return http.build();
	}
}
