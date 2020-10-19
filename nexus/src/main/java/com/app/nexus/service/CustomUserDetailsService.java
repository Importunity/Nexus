package com.app.nexus.service;

import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author Amadeus
 * used to load a users data given the username
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;


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
