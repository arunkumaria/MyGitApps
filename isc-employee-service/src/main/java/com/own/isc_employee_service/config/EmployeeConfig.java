package com.own.isc_employee_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.own.isc_address_service.service.*;

//@Configuration
//public class EmployeeConfig {
//
//    @Bean
//    public ModelMapper modelMapperBean() {
//        return new ModelMapper();
//    }
//
//}

@Configuration
public class EmployeeConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplateBean() {
		return new RestTemplate();
	}

}
