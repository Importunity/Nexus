package com.app.nexus.service;

import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author Amadeus
 * used to load a users data given the username
 * since organization is a user with different attributes we'll be using this class for
 * application user and organization user
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    @Transactional
    // loads the application user if exists with the email or username entered otherwise throw a usernamenotfoundexception
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Username or Email"));
        return UserPrincipal.create(applicationUser);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        // loads the application user by the Id otherwwise throw usernamenotfound
        ApplicationUser applicationUser = applicationUserRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Application user not found with id : " + id)
        );

        return UserPrincipal.create(applicationUser);
    }






}
