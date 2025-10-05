package com.own.transaction_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.transaction_management.model.Address;
import com.own.transaction_management.model.Employee;
import com.own.transaction_management.repository.EmployeeRepository;

//@Service
//public class EmployeeService {
//
//	@Autowired
//	private EmployeeRepository employeeRepository;
//
//	@Autowired
//	private AddressService addressService;
//
//	@Transactional(rollbackFor = Exception.class)
//	public Employee addEmployee(Employee employee) throws Exception {
//		Employee employeeSavedToDB = this.employeeRepository.save(employee);
//
//		// Create address for employee
//		Address address = new Address();
//		address.setAddress("Varanasi");
//		address.setEmployee(employee);
//
//		// This may throw an exception intentionally for testing rollback
//		if (employee.getName().equalsIgnoreCase("error")) {
//			throw new RuntimeException("Simulated Exception: Forcing rollback!");
//		}
//
//		this.addressService.addAddress(address);
//		return employeeSavedToDB;
//	}
//}


//@Service
//public class EmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private AddressService addressService;
//
//    public Employee addEmployee(Employee employee) throws Exception {
//        Employee employeeSavedToDB = this.employeeRepository.save(employee);
//
//        // Address object initialized as null
//        Address address = null; 
//        address.setId(123L); // This will throw NullPointerException
//        address.setAddress("Varanasi");
//        address.setEmployee(employee);
//
//        // Save address
//        this.addressService.addAddress(address);
//
//        return employeeSavedToDB;
//    }
//}


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressService addressService;

    @Transactional(rollbackFor = Exception.class)
    public Employee addEmployee(Employee employee) throws Exception {
        Employee employeeSavedToDB = this.employeeRepository.save(employee);

        // Intentional null to simulate failure
        Address address = null;
        address.setId(123L);
        address.setAddress("Varanasi");
        address.setEmployee(employee);

        this.addressService.addAddress(address);

        return employeeSavedToDB;
    }
}

