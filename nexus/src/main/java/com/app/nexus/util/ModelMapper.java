package com.app.nexus.util;

import com.app.nexus.information.UserSummary;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Task;
import com.app.nexus.response.TaskResponse;

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
}
