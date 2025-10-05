package com.own.springboot_crud;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class Controller2 {

	private final UserRepository2 repository2;

	public Controller2(UserRepository2 repository2) {
		super();
		this.repository2 = repository2;
	}

	@PostMapping
	public User2 postUser(@RequestBody User2 user) {
		return repository2.save(user);
	}
	
	@GetMapping
	public List<User2> getAllUsers() {
		return repository2.findAll();
	}

	
	@GetMapping("/{id}")
	public User2 getUserById(@PathVariable long id) {
		
		return repository2.findById(id).orElseThrow(()->new UserNotFoundException2(id));
		
	}
	
	@PutMapping("/update/{id}")
	public User2 update(@PathVariable long id, @RequestBody User2 user2) {
		
		return repository2.findById(id).map(existing->{existing.setName(user2.getName()); existing.setEmail(user2.getEmail());return repository2.save(existing);}).orElse(null);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id) {
		System.out.println("====delete mapping=====");
		repository2.deleteById(id);
	}
}
