package com.app.nexus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @Author Amadeus
 */
@Entity
@Table(name = "user_projects")
public class ApplicationUserProject {

    @EmbeddedId
    private ApplicationUserProjectId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("projectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    private ApplicationUser user;


    public ApplicationUserProject(){}

    public ApplicationUserProject(ApplicationUserProjectId id, Project project, ApplicationUser user) {
        this.id = id;
        this.project = project;
        this.user = user;
    }

    public ApplicationUserProjectId getId() {
        return id;
    }

    public void setId(ApplicationUserProjectId id) {
        this.id = id;
    }

    @JsonIgnore
    public Project getProject() {
        return project;
    }

    @JsonIgnore
    public void setProject(Project project) {
        this.project = project;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

}
