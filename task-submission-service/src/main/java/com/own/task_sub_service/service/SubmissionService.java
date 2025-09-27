package com.own.task_sub_service.service;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.own.task_sub_service.*;
import com.own.task_sub_service.model.TaskSubmission;

@Component
public interface SubmissionService {

	TaskSubmission submitTask(String taskId, String githubLink, String userId, String jwt) throws Exception;

	TaskSubmission getTaskSubmissionById(String submissionId) throws Exception;

	List<TaskSubmission> getAllTaskSubmissions();

	List<TaskSubmission> getTaskSubmissionByTaskId(String taskId);

	TaskSubmission acceptDeclineSubmission(String id, String status) throws Exception;
}