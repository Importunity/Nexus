package com.app.nexus.controller;

import com.app.nexus.exception.AppException;
import com.app.nexus.exception.BadRequestException;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.JWTRefreshToken;
import com.app.nexus.model.Role;
import com.app.nexus.model.RoleName;
import com.app.nexus.payload.request.RefreshTokenRequest;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.JWTRefreshTokenRepository;
import com.app.nexus.repository.RoleRepository;
import com.app.nexus.payload.request.LoginRequest;
import com.app.nexus.payload.request.RegisterRequest;
import com.app.nexus.payload.response.APIResponse;
import com.app.nexus.payload.response.JWTAuthenticationResponse;
import com.app.nexus.security.JWTProvider;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    JWTRefreshTokenRepository jwtRefreshTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTProvider jwtProvider;

    @Value("${app.jwtExpiration}")
    private Long jwtExpiration;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateApplicationUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        String accessToken = jwtProvider.generateToken(userPrincipal);
        String refreshToken = jwtProvider.generateRefreshToken();

        saveRefreshToken(userPrincipal, refreshToken);
        return ResponseEntity.ok(new JWTAuthenticationResponse(accessToken, refreshToken, jwtExpiration));
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

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return jwtRefreshTokenRepository.findById(refreshTokenRequest.getRefreshToken()).map(jwtRefreshToken -> {
            ApplicationUser applicationUser = jwtRefreshToken.getApplicationUser();
            String accessToken = jwtProvider.generateToken(UserPrincipal.create(applicationUser));
            jwtRefreshToken.setApplicationUser(null);
            return ResponseEntity.ok(new JWTAuthenticationResponse(accessToken, jwtRefreshToken.getToken(), jwtExpiration));
        }).orElseThrow(() -> new BadRequestException("Invalid Refresh Token"));
    }

    private void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken){
        JWTRefreshToken jwtRefreshToken = new JWTRefreshToken(refreshToken);
        jwtRefreshToken.setApplicationUser(applicationUserRepository.getOne(userPrincipal.getId()));
        Instant expirationDateTime = Instant.now().plus(1, ChronoUnit.HOURS);
        jwtRefreshToken.setExpirationDate(expirationDateTime);
        jwtRefreshTokenRepository.save(jwtRefreshToken);
    }


}
