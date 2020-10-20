package com.app.nexus.controller;

import com.app.nexus.exception.AppException;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Role;
import com.app.nexus.model.RoleName;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.RoleRepository;
import com.app.nexus.request.LoginRequest;
import com.app.nexus.request.RegisterRequest;
import com.app.nexus.response.APIResponse;
import com.app.nexus.response.JWTAuthenticationResponse;
import com.app.nexus.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

/**
 * @Author Amadeus
 * login and register api for authenticating user
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateApplicationUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generates a token for authentication
        String token = jwtProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        if(applicationUserRepository.existsByUsername(registerRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Username Exists"), HttpStatus.BAD_REQUEST);
        }

        if(applicationUserRepository.existsByEmail(registerRequest.getUsername())){
            return new ResponseEntity<>(new APIResponse(false, "Email Exists"), HttpStatus.BAD_REQUEST);
        }

        ApplicationUser applicationUser = new ApplicationUser(registerRequest.getFirst(), registerRequest.getLast(),
                registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPhone(),
                registerRequest.getPassword());

        applicationUser.setPassword_hash(passwordEncoder.encode(applicationUser.getPassword_hash()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        applicationUser.setRoles(Collections.singleton(userRole));

        ApplicationUser saveUser = applicationUserRepository.save(applicationUser);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(saveUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(new APIResponse(true, "User registered successfully"));
    }
}
