package com.own.isc_employee_service.service;


import com.own.isc_address_service.entity.*;
import com.own.isc_address_service.repo.*;
import com.own.isc_address_service.response.*;
import com.own.isc_employee_service.entity.Employee;
import com.own.isc_employee_service.repo.EmployeeRepo;
import com.own.isc_employee_service.response.EmployeeResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class EmployeeService {
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    @Autowired
//    private ModelMapper mapper;
//
//    public EmployeeResponse getEmployeeById(int id) {
//        Optional<Employee> employee = employeeRepo.findById(id);
//        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
//        return employeeResponse;
//    }
//
//}



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public EmployeeResponse getEmployeeById(int id) {

        Optional<Employee> employee = employeeRepo.findById(id);
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

        AddressResponse addressResponse = restTemplate.getForObject("http://localhost:8081/address-service/address/{id}", AddressResponse.class, id);
        employeeResponse.setAddressResponse(addressResponse);

        return employeeResponse;
    }

}