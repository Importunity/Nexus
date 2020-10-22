package com.app.nexus.response;

import com.app.nexus.information.UserSummary;
import com.app.nexus.model.ApplicationUser;

import java.time.Instant;

/**
 * @Author Amadeus
 * Task response is a class that is used to show to the user what is shown in the json
 */

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private UserSummary createdBy;
    private Instant taskCreated;
    // checks to see if the task has been completed
    private Boolean isCompleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getTaskCreated() {
        return taskCreated;
    }

    public void setTaskCreated(Instant taskCreated) {
        this.taskCreated = taskCreated;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
