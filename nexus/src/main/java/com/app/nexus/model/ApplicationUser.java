package com.app.nexus.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @Author Amadeus
 */

@Entity
@Table(name="users")
@Data
public class ApplicationUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name= "first")
    private String first;

    @NotBlank
    @Size(max = 20)
    @Column(name="last")
    private String last;

    @NotBlank
    @Size(max=20)
    @Column(name="username")
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="email_confirmed")
    private boolean email_confirmed; // 0 - false, 1 - true

    @NotBlank
    @Size(max = 20)
    @Column(name="phone_number")
    private String phoneNumber;

    @NotBlank
    @Column(name="phone_confirmed")
    private boolean phone_confirmed;

    @NotBlank
    @Column(name="two_factor_enabled")
    private boolean two_factor_enabled;

    @NotBlank
    @Column(name="password_hash")
    private String password_hash;

    // many can application users are associated with one organization
    @JoinColumn(name="organization_id")
    @ManyToOne
    private Organization organization;

    // user can have many roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;


}
