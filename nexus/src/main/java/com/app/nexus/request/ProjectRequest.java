package com.app.nexus.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author Amadeus
 */

public class ProjectRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Size(max = 100)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
