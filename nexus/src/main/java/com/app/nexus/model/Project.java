package com.app.nexus.model;

import com.app.nexus.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @Author Amadeus
 */

@Entity
//@Data
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
    @Size(max = 300)
    private String description;


    // a project can have multiple tasks
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ApplicationUserProject> users;


    public Set<ApplicationUserProject> getUsers() {
        return users;
    }

    public void setUsers(Set<ApplicationUserProject> users) {
        this.users = users;
    }


    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

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

    public void addTask(Task task){
        this.tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.setProject(null);
    }


}
