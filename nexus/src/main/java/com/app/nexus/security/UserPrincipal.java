package com.app.nexus.security;

import com.app.nexus.model.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Amadeus
 * Spring security will use the information stored in this object to perform authentication and authorization
 */

public class UserPrincipal implements UserDetails {
    private Long id;
    private String first;
    private String last;
    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String first, String last, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(ApplicationUser applicationUser){
        List<GrantedAuthority> authorities = applicationUser
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                applicationUser.getId(),
                applicationUser.getFirst(),
                applicationUser.getLast(),
                applicationUser.getUsername(),
                applicationUser.getEmail(),
                applicationUser.getPassword_hash(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
