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
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
// lombok
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(name="email")
    private String email;

    @NotBlank
    @Size(max = 40)
    private String password;


    // an organization can have multiple application users
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private Set<ApplicationUser> applicationUsers;

}
