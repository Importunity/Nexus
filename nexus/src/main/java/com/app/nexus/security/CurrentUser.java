package com.app.nexus.security;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * @Amadeus
 * The meta-annotation is created so if we want to remove spring security from the project we can change
 * the currentuser annotation.
 */

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// the authentication principal annotation allows us to access the current authenticated user in controllers
@AuthenticationPrincipal
public @interface CurrentUser {
}
