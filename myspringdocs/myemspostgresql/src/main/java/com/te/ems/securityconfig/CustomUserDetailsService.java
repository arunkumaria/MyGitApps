package com.te.ems.securityconfig;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority> roles = null;
		if(username.equals("admin")) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
			return new User("admin","$2a$10$/.iZz8bBKQ/GCetBPJh0..FZIYiA73dm9cJrJaA1eW/IW9NDC0FE2", roles);
		}
		
		else if(username.equals("user")) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
			return new User("user","$2a$10$xpkUGjOgUfDkynD5abnD2.lpfz5SGVGssj0FaX1013CSIso6Qelee", roles);
		}
		
		
		return (UserDetails) new UsernameNotFoundException("user not found"+username);
	}
	
	

}
