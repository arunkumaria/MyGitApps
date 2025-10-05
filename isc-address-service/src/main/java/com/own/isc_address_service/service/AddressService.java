package com.own.isc_address_service.service;

import com.own.isc_address_service.entity.*;
import com.own.isc_address_service.repo.*;
import com.own.isc_address_service.response.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private ModelMapper mapper;

	public AddressResponse findAddressByEmployeeId(int employeeId) {
		Optional<Address> addressByEmployeeId = addressRepo.findAddressByEmployeeId(employeeId);
		AddressResponse addressResponse = mapper.map(addressByEmployeeId, AddressResponse.class);
		return addressResponse;
	}

}