package com.own.microservice_openfeign_emp_service.service;

import com.own.microservice_openfeign_emp_service.entity.*;
import com.own.microservice_openfeign_emp_service.*;
import com.own.microservice_openfeign_emp_service.repo.*;
import com.own.microservice_openfeign_emp_service.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AddressClient addressClient;

	public EmployeeResponse getEmployeeById(int id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

		// map Employee -> EmployeeResponse
		EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

		// fetch address using Feign client
		AddressResponse addressResponse = addressClient.getAddressByEmployeeId(id);
		employeeResponse.setAddressResponse(addressResponse);

		return employeeResponse;
	}
}
