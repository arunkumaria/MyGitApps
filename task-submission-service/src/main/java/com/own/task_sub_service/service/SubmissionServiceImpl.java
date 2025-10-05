package com.own.task_sub_service.service;

//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.own.task_sub_service.model.TaskSubmission;
//
//@Service
//public class SubmissionServiceImpl implements SubmissionService {
//
//	@Override
//	public TaskSubmission submitTask(String taskId, String githubLink, String userId, String jwt) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TaskSubmission getTaskSubmissionById(String submissionId) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<TaskSubmission> getAllTaskSubmissions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<TaskSubmission> getTaskSubmissionByTaskId(String taskId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TaskSubmission acceptDeclineSubmission(String id, String status) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}

import com.own.task_sub_service.service.*;
import com.own.task_sub_service.model.*;
import com.own.task_sub_service.service.*;
import java.time.LocalDateTime;

import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubmissionServiceImpl implements SubmissionService {

	private final Map<String, TaskSubmission> submissions = new ConcurrentHashMap<>();

	@Autowired
	private TaskService taskClient;

	@Override
	public TaskSubmission submitTask(String taskId, String githubLink, String userId, String jwt) throws Exception {
		// Optional: Validate JWT (if using Auth service)
		if (jwt == null || jwt.isEmpty()) {
			throw new Exception("JWT token is required");
		}

		// Fetch task to verify it exists
		TaskDTO task = taskClient.getTaskById(taskId, jwt);
		if (task == null) {
			throw new Exception("Invalid task ID");
		}

		// Create submission
		String submissionId = UUID.randomUUID().toString();
		TaskSubmission submission = new TaskSubmission();
		submission.setId(submissionId);
		submission.setTaskId(taskId);
		submission.setGithubLink(githubLink);
		submission.setUserId(userId);
		submission.setStatus("PENDING");
		submission.setSubmissionTime(LocalDateTime.now());

		submissions.put(submissionId, submission);
		return submission;
	}

	@Override
	public TaskSubmission getTaskSubmissionById(String submissionId) throws Exception {
		TaskSubmission submission = submissions.get(submissionId);
		if (submission == null) {
			throw new Exception("Submission not found");
		}
		return submission;
	}

	@Override
	public List<TaskSubmission> getAllTaskSubmissions() {
		return new ArrayList<>(submissions.values());
	}

	@Override
	public List<TaskSubmission> getTaskSubmissionByTaskId(String taskId) {
		List<TaskSubmission> result = new ArrayList<>();
		for (TaskSubmission submission : submissions.values()) {
			if (submission.getTaskId().equals(taskId)) {
				result.add(submission);
			}
		}
		return result;
	}

	@Override
	public TaskSubmission acceptDeclineSubmission(String id, String status) throws Exception {
		TaskSubmission submission = submissions.get(id);
		if (submission == null) {
			throw new Exception("Submission not found");
		}

		if (!status.equalsIgnoreCase("ACCEPTED") && !status.equalsIgnoreCase("DECLINED")) {
			throw new Exception("Invalid status: must be ACCEPTED or DECLINED");
		}

		submission.setStatus(status.toUpperCase());
		return submission;
	}
}
