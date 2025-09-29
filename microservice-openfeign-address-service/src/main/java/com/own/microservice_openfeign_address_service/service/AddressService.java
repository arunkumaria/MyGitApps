package com.own.microservice_openfeign_address_service.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.microservice_openfeign_address_service.entity.*;
import com.own.microservice_openfeign_address_service.repo.*;
import com.own.microservice_openfeign_address_service.response.*;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private ModelMapper mapper;

    public AddressResponse findAddressByEmployeeId(int employeeId) {
        Address address = addressRepo.findAddressByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Address not found for employeeId: " + employeeId));

        // map Address → AddressResponse
        return mapper.map(address, AddressResponse.class);
    }
}