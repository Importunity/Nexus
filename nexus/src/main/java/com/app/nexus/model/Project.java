package com.app.nexus.model;

import com.app.nexus.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // a project can have multiple tasks
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks;
}
