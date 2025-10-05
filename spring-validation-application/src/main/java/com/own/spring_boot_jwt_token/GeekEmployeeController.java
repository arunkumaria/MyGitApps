package com.own.spring_boot_jwt_token;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geek")
public class GeekEmployeeController {

	@PostMapping("/addEmployee")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody GeekEmployee employee) {
		return new ResponseEntity<>("Employee details are valid and added successfully! \n" + "Name: "
				+ employee.getGeekEmployeeName() + "\n" + "Email: " + employee.getGeekEmailId() + "\n" + "Salary: "
				+ employee.getSalary() + "\n" + "Qualifications: " + employee.getQualifications(), HttpStatus.OK);
	}

	@GetMapping("/check")
	public String check() {
		return "verified";
	}
}
