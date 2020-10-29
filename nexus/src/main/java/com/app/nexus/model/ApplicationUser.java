package com.app.nexus.model;

import com.app.nexus.audit.DateAudit;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Amadeus
 */

@Entity
@Table(name="users")
//@Data
public class ApplicationUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // first name can be up to 50 characters long according to MN law
    @NotBlank
    @Size(max = 50)
    @Column(name= "first")
    private String first;

    @NotBlank
    @Size(max = 50)
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

    //@NotBlank
    @Column(name="email_confirmed")
    private boolean email_confirmed; // 0 - false, 1 - true

    @NotBlank
    @Size(max = 20)
    @Column(name="phone_number")
    private String phoneNumber;

    //@NotBlank
    @Column(name="phone_confirmed")
    private boolean phone_confirmed;

    //@NotBlank
    @Column(name="two_factor_enabled")
    private boolean two_factor_enabled;

    @NotBlank
    @Column(name="password_hash")
    private String password_hash;


    // user can have many roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;




    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    Set<ApplicationUserProject> projects;

    // users can have many tasks
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public ApplicationUser(){}

    public ApplicationUser(@NotBlank @Size(max = 50) String first, @NotBlank @Size(max = 50) String last, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 20) String phoneNumber, @NotBlank String password_hash) {
        this.first = first;
        this.last = last;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password_hash = password_hash;
    }


    public Set<ApplicationUserProject> getProjects() {
        return projects;
    }

    public void setProjects(Set<ApplicationUserProject> projects) {
        this.projects = projects;
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_confirmed() {
        return email_confirmed;
    }

    public void setEmail_confirmed(boolean email_confirmed) {
        this.email_confirmed = email_confirmed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhone_confirmed() {
        return phone_confirmed;
    }

    public void setPhone_confirmed(boolean phone_confirmed) {
        this.phone_confirmed = phone_confirmed;
    }

    public boolean isTwo_factor_enabled() {
        return two_factor_enabled;
    }

    public void setTwo_factor_enabled(boolean two_factor_enabled) {
        this.two_factor_enabled = two_factor_enabled;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

/*    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }*/

    public void addTask(Task task){
        tasks.add(task);
        task.setUser(this);
    }
    public void removeTask(Task task){
        tasks.remove(task);
        task.setUser(null);
    }

    // testing
    public void addProject(Project project){
        ApplicationUserProject applicationUserProject = new ApplicationUserProject(new ApplicationUserProjectId(this.getId(), project.getId()), project, this);
        this.projects.add( applicationUserProject);
    }

    public Set<ApplicationUserProjectId> getProjectIds(){
        return this.projects
                .stream()
                .map(ApplicationUserProject::getId)
                .collect(Collectors.toSet());
    }
    //


    /*@Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        ApplicationUser applicationUser = (ApplicationUser) obj;
        return Objects.equals(username, applicationUser.getUsername());
    }*/
}
