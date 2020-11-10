package com.app.nexus.controller;

import com.app.nexus.information.UserPrincipal;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.payload.request.TaskRequest;
import com.app.nexus.payload.response.TaskResponse;
import com.app.nexus.security.CurrentUser;
import com.app.nexus.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author Amadeus
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @PostMapping("/{projectId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest, @CurrentUser UserPrincipal currentUser, @PathVariable Long projectId){
        taskService.createTask(taskRequest, currentUser, projectId);

    }


    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@CurrentUser UserPrincipal currentUser, @PathVariable Long taskId){
        return taskService.getTaskById(taskId, currentUser);
    }
}
