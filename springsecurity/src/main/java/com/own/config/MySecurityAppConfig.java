package com.own.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class MySecurityAppConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Configure in-memory user authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("gfg").password(passwordEncoder.encode("gfg123")).roles("ADMIN");
	}

	// Configure HTTP security (basic authentication + form login)
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .and()
	        .httpBasic()
	            .and()
	        .csrf().disable(); // Disable CSRF
	}
}
