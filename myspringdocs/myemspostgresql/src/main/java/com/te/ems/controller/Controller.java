
package com.te.ems.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.ems.dao.EmpDao;
import com.te.ems.service.EmpService;
import com.te.ems.dto.EmployeeBean;

@RestController
@RequestMapping("/empuser")

public class Controller {

	@Autowired
	EmpService userService;

	@Autowired
	EmpDao userDao;

//adding & updating

	@PostMapping(path = "/employeeregister")
	public String userValidate(@RequestBody EmployeeBean employeeBean) {

		System.out.println("enters user validate");

		System.out.println(employeeBean.getId());
		System.out.println(employeeBean.getSalary());
		System.out.println(employeeBean.getMobile());
		System.out.println(employeeBean.getEmail());
		System.out.println(employeeBean.getFirstName());
		System.out.println(employeeBean.getLastName());
		System.out.println("--------------------------");

		Integer id = employeeBean.getId();
		String firstname = employeeBean.getFirstName();
		String lastName = employeeBean.getLastName();
		String email = employeeBean.getEmail();
		long mobile = employeeBean.getMobile();
		Double salary = employeeBean.getSalary();

		employeeBean.setId(id);
		employeeBean.setFirstName(firstname);
		employeeBean.setLastName(lastName);
		employeeBean.setEmail(email);
		employeeBean.setMobile(mobile);
		employeeBean.setSalary(salary);

		if (userService.addRegister(employeeBean) == true) {
			return "success";
		} else {
			return "failure";
		}

	}

//reading

	@GetMapping(path = "/employeedisplay")
	public ResponseEntity<?> getEmployees() {

		List<EmployeeBean> employees = userService.readRegister();

		Optional<List<EmployeeBean>> checkNull = Optional.ofNullable(employees);

		

		if (checkNull.isEmpty() != false) {
			return ResponseEntity.ok(employees);
		} else {
			return ResponseEntity.ok("no employees present");
		}

	}

//deleting

	@DeleteMapping(path = "/employeedelete")
	public String userDelete(@RequestBody EmployeeBean employeeBean) {

		System.out.println("enters user validate");

		System.out.println(employeeBean.getFirstName());

		Integer id = employeeBean.getId();
		String firstName = employeeBean.getFirstName();
		String lastName = employeeBean.getLastName();

		String email = employeeBean.getEmail();
		long mobile = employeeBean.getMobile();
		Double salary = employeeBean.getSalary();

		employeeBean.setId(id);
		employeeBean.setFirstName(firstName);
		employeeBean.setLastName(lastName);
		employeeBean.setEmail(email);
		employeeBean.setMobile(mobile);
		employeeBean.setSalary(salary);

		if (userService.deleteRegister(employeeBean) == true) {
			return "success";
		} else {
			return "failure";
		}

	}
	
	

}
