package com.own.task_service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.own.task_service.model.*;

import java.util.List;
import java.util.Optional;

@Repository

public interface TaskRepository extends MongoRepository<Task, String> {

	public List<Task> findByassignedUserId(String userId);

	public void deleteById(String id);

	public Task getTaskById(String id);
}