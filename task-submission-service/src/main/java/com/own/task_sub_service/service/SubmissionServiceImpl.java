package com.own.task_sub_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.own.task_sub_service.model.TaskSubmission;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	@Override
	public TaskSubmission submitTask(String taskId, String githubLink, String userId, String jwt) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskSubmission getTaskSubmissionById(String submissionId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskSubmission> getAllTaskSubmissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskSubmission> getTaskSubmissionByTaskId(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskSubmission acceptDeclineSubmission(String id, String status) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
