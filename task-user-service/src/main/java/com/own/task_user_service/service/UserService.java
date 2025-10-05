package com.own.task_user_service.service;

import com.own.task_user_service.exception.*;
import com.own.task_user_service.model.*;
import java.util.List;

public interface UserService {

	public List<User> getAllUser() throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;

	public User findUserByEmail(String email) throws UserException;

	public User findUserById(String userId) throws UserException;

	public List<User> findAllUsers();
}
