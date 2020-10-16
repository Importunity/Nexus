package com.app.nexus.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @Author Amadeus
 */

@Entity
@Data
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name= "name")
    @Size(max = 50)
    private String name;

    @NotBlank
    @Column(name = "email")
    @Email
    private String email;

    // a client can have more than one project
    @NotBlank
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Project> projects;
}
