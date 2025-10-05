package com.own.task_service.service;

import java.util.List;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.own.task_service.model.UserDTO;


//connect with taskUserService microService
@FeignClient(name = "user-SERVICE",url = "http://localhost:8081")
public interface UserService { 

    @GetMapping("/api/users/profile")
	public UserDTO getUserProfileHandler(@RequestHeader("Authorization") String jwt);
}