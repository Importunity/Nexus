package com.app.nexus.controller;

import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.Project;
import com.app.nexus.model.Task;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import com.app.nexus.payload.request.ProjectRequest;
import com.app.nexus.payload.request.TaskRequest;
import com.app.nexus.payload.response.APIResponse;
import com.app.nexus.payload.response.ProjectResponse;
import com.app.nexus.security.CurrentUser;
import com.app.nexus.service.ProjectService;
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
import java.util.List;

/**
 * @Author Amadeus
 */

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest projectRequest, @CurrentUser UserPrincipal currentUser){

        Project project = projectService.createProject(projectRequest, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{projectId}")
                .buildAndExpand(project.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new APIResponse(true, "Project Created"));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<ProjectResponse> getProjects(@CurrentUser UserPrincipal currentUser){
        return projectService.getProjects(currentUser);
    }

    @PostMapping("/{projectId}/tasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest taskRequest, @CurrentUser UserPrincipal currentUser, @PathVariable Long projectId){
        Task task = taskService.createTask(taskRequest, currentUser, projectId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{projectId}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new APIResponse(true, "Task Created"));
    }

    @DeleteMapping("/{projectId}/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public Task removeTask(@PathVariable Long taskId, @PathVariable Long projectId, @CurrentUser UserPrincipal currentUser){
        Task task = taskService.removeTask(taskId, projectId, currentUser);

        return task;
    }

    @PutMapping("/{projectId}/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public Task updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest){
        Task task = taskService.updateTask(taskId, projectId, taskRequest);
        return task;
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("hasRole('USER')")
    public Project removeProject(@PathVariable Long projectId, @CurrentUser UserPrincipal currentUser){
        Project project = projectService.removeProject(currentUser, projectId);
        return project;
    }


}
