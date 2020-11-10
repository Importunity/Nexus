package com.app.nexus.service;

import com.app.nexus.exception.ResourceNotFoundException;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.*;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.payload.request.ProjectRequest;
import com.app.nexus.payload.response.ProjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author Amadeus
 */

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


    public Project createProject(ProjectRequest projectRequest, UserPrincipal currentUser){
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", currentUser.getId()));
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        applicationUser.addProject(project);
        //applicationUserRepository.save(applicationUser);
        return projectRepository.save(project);
    }

    public List<ProjectResponse> getProjects(UserPrincipal currentUser){
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", currentUser.getId()));
        Set<ApplicationUserProject> applicationUserProjects = applicationUser.getProjects();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for(ApplicationUserProject applicationUserProject : applicationUserProjects){
            ProjectResponse projectResponse = new ProjectResponse();
            projectResponse.setId(applicationUserProject.getProject().getId());
            projectResponse.setName(applicationUserProject.getProject().getName());
            projectResponse.setDescription(applicationUserProject.getProject().getDescription());
            projectResponse.setTasks(applicationUserProject.getProject().getTasks());
            projectResponses.add(projectResponse);
        }
        return projectResponses;
    }

    public Project removeProject(UserPrincipal currentUser, Long projectId){
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Application User", "id", currentUser.getId()));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project" , "id", projectId));
        for(Task task : project.getTasks()){
            project.removeTask(task);
            task.getUser().removeTask(task);
        }
        applicationUser.removeProject(project);
        return projectRepository.save(project);
    }



}
