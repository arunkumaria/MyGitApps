package com.own.springboot_10best.crud;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserRepository repo;
	private final com.example.toolkit.async.EmailService emailService;
	private final com.example.toolkit.external.WeatherService weather;

	public UserController(UserRepository repo, com.example.toolkit.async.EmailService emailService,
			com.example.toolkit.external.WeatherService weather) {
		this.repo = repo;
		this.emailService = emailService;
		this.weather = weather;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User u) {
		return repo.save(u);
	}

	@GetMapping
	public List<User> all() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable Long id) {
		return repo.findById(id).orElseThrow();
	}

	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody User u) {
		return repo.findById(id).map(x -> {
			x.setName(u.getName());
			x.setEmail(u.getEmail());
			return repo.save(x);
		}).orElseThrow();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repo.deleteById(id);
	}

	@PostMapping("/{id}/send-welcome")
	public String welcome(@PathVariable Long id) {
		User u = repo.findById(id).orElseThrow();
		emailService.sendEmail(u.getEmail());
		return "Triggered async email to " + u.getEmail();
	}

	@GetMapping("/weather/{city}")
	public String weather(@PathVariable String city) {
		return weather.getWeatherEcho(city);
	}
}