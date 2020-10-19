package com.app.nexus.controller;

import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.Task;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.request.TaskRequest;
import com.app.nexus.response.APIResponse;
import com.app.nexus.response.TaskResponse;
import com.app.nexus.security.CurrentUser;
import com.app.nexus.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest taskRequest){
        Task task = taskService.createTask(taskRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new APIResponse(true, "Task Created"));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@CurrentUser UserPrincipal currentUser, @PathVariable Long taskId){
        return taskService.getTaskById(taskId, currentUser);
    }
}
