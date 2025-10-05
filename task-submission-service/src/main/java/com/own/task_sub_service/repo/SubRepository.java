package com.own.task_sub_service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.own.task_sub_service.model.*;

@Repository
public interface SubRepository extends MongoRepository<TaskSubmission, String> {
	// List<TaskSubmission> findByTaskId(String taskId);
}