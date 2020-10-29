package com.app.nexus.controller;

import com.app.nexus.information.UserIdentityAvailability;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.information.UserSummary;
import com.app.nexus.model.Project;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.request.ProjectRequest;
import com.app.nexus.response.ProjectResponse;
import com.app.nexus.response.TaskResponse;
import com.app.nexus.security.CurrentUser;
import com.app.nexus.service.CustomUserDetailsService;
import com.app.nexus.service.ProjectService;
import com.app.nexus.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Author Amadeus
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        //System.out.println(currentUser.getFirst());
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFirst(), currentUser.getLast());
        return userSummary;
    }

    @GetMapping("/user/project")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUserProjects(@CurrentUser UserPrincipal currentUser){
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getFirst(), currentUser.getLast());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username){
        boolean isAvailable = !applicationUserRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !applicationUserRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/{username}/tasks")
    public List<TaskResponse> getTasksCreatedBy(@PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser){
        return taskService.getTasksCreatedBy(username, currentUser);
    }



}
