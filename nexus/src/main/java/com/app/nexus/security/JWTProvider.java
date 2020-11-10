package com.app.nexus.security;

import com.app.nexus.information.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Amadeus
 * this class is used for generating a Json Web Token after the user logs in.
 * this class is used for validating the Json Web Token sent in the Authorization header
 */

// the annotation means that only a single instance of JWTProvider is created/
@Component
public class JWTProvider {
    private static final Logger logger = LoggerFactory.getLogger(JWTProvider.class);

    // retrieves the environmental variable secret key from application.properties file
    @Value("${app.jwtSecret")
    private String jwtSecret;

    @Value("${app.jwtExpiration}")
    private long jwtExpiration;



    public String generateToken(UserPrincipal userPrincipal){
        // authenticates the user
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    // retrieves the user id from json web token
    public Long getUserId(String token){
        // claims are statements about an entity
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}
