package com.app.nexus.response;

/**
 * @Author Amadeus
 * jwt authentication response to retrieve the bearer token
 * a bearer is a piece of information that you can present to some service
 * that by virtue of you having it grants you access to something
 */

public class JWTAuthenticationResponse {
    private String token;
    private String bearer = "Bearer";

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
