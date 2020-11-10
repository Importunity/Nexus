package com.app.nexus.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * @Author Amadeus
 */

public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
