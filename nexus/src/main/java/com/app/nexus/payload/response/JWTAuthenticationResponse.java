package com.app.nexus.payload.response;

/**
 * @Author Amadeus
 * jwt authentication response to retrieve the bearer token
 * a bearer is a piece of information that you can present to some service
 * that by virtue of you having it grants you access to something
 */

public class JWTAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String bearer = "Bearer";
    private Long expires;

    public JWTAuthenticationResponse(String token, String refreshToken, Long expires) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expires = expires;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public JWTAuthenticationResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
