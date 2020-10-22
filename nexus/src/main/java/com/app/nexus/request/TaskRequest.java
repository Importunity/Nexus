package com.app.nexus.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author Amadeus
 * This class is used whenever a user makes a request to create a task
 */

public class TaskRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String description;


    @NotNull(message = "please enter in a level")
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
