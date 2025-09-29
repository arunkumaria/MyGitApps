package com.own.microservice_openfeign_emp_service.service;

import com.own.microservice_openfeign_emp_service.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-service", url = "http://localhost:8081", path = "/address-service")
public interface AddressClient {

	@GetMapping("/address/{id}")
	public AddressResponse getAddressByEmployeeId(@PathVariable("id") int id);
}