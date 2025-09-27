package com.own.task_sub_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.own.task_sub_service.model.*;

@FeignClient(name = "TASK-SERVICE", url = "http://localhost:8082")
public interface TaskService {

	@GetMapping("/api/tasks/{id}")
	TaskDTO getTaskById(@PathVariable String id, @RequestHeader("Authorization") String jwt) throws Exception;

	@PutMapping("/api/tasks/{id}/complete")
	TaskDTO completeTask(@PathVariable String id);
}