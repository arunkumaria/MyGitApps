package com.own.sdj.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.own.sdj.model.GeekUserRecord;
import com.own.sdj.repo.GeekUserRepository;

import java.util.List;

@Service
@Transactional
public class GeekUserService {

	private final GeekUserRepository repository;

	public GeekUserService(GeekUserRepository repository) {
		this.repository = repository;
	}

	public List<GeekUserRecord> getAllGeekUsers() {
		return repository.findAll();
	}

	public GeekUserRecord addGeekUser(GeekUserRecord userRecord) {
		return repository.save(userRecord);
	}
}
