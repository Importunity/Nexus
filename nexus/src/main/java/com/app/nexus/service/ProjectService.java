package com.app.nexus.service;

import com.app.nexus.exception.ResourceNotFoundException;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Project;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import com.app.nexus.request.ProjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Amadeus
 */

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


    public Project createProject(ProjectRequest projectRequest, UserPrincipal currentUser){
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        applicationUser.getProjects().add(project);
        //project.getApplicationUsers().add(applicationUser);
        return projectRepository.save(project);
    }
}
