package com.app.nexus.model;

import com.app.nexus.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @Author Amadeus
 */

@Entity
@Data
@Table(name="projects")
public class Project extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @NotBlank
    @Column(name = "description")
    @Size(max = 100)
    private String description;

    // a project can have multiple tasks
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;

    //@ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    //private Set<ApplicationUser> applicationUsers;

    public Project(){}

    public Project(Long id, @NotBlank String name) {
        this.id = id;
        this.name = name;
    }

    /*public Set<ApplicationUser> getApplicationUsers() {
        return applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        this.applicationUsers = applicationUsers;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
