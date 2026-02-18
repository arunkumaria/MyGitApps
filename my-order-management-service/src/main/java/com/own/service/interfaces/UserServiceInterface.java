package com.own.service.interfaces;

import com.own.dto.AuthRequest;
import com.own.model.User;

public interface UserServiceInterface {

	public User registerService(AuthRequest authRequest);

	public String loginService(AuthRequest authRequest);

}
