package com.app.nexus.controller;

import com.app.nexus.information.UserIdentityAvailability;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.information.UserSummary;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.security.CurrentUser;
import com.app.nexus.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
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


}
