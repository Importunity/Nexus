package com.app.nexus.util;

import com.app.nexus.information.UserSummary;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Project;
import com.app.nexus.model.Task;
import com.app.nexus.payload.response.ProjectResponse;
import com.app.nexus.payload.response.TaskResponse;

/**
 * @Author Amadeus
 */

public class ModelMapper {

    public static TaskResponse mapToTaskResponse(Task task, ApplicationUser creator){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        UserSummary userSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getFirst(), creator.getLast());
        taskResponse.setCreatedBy(userSummary);

        return taskResponse;
    }

    public static ProjectResponse mapToProjectResponse(Project project){
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setDescription(project.getDescription());
        return projectResponse;
    }
}
