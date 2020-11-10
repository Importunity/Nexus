package com.app.nexus.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * @Author Amadeus
 */

@Entity
@Table(name="refresh_tokens")
public class JWTRefreshToken {
    @Id
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser applicationUser;

    private Instant expirationDate;

    public JWTRefreshToken(){}

    public JWTRefreshToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }
}
