package com.own.transaction_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.transaction_management.model.Address;
import com.own.transaction_management.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Address addAddress(Address address) {
		return this.addressRepository.save(address);
	}
}
