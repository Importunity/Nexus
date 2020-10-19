package com.app.nexus.service;

import com.app.nexus.exception.ResourceNotFoundException;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Task;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.request.TaskRequest;
import com.app.nexus.response.TaskResponse;
import com.app.nexus.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @Author Amadeus
 */

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public Task createTask(TaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setLevel(taskRequest.getLevel());
        Instant now = Instant.now();
        return taskRepository.save(task);
    }

    public TaskResponse getTaskById(Long taskId, UserPrincipal currentUser){
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        ApplicationUser creator = applicationUserRepository
                .findById(task.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", task.getCreatedBy()));


        return ModelMapper.mapToTaskResponse(task, creator);
    }
}
