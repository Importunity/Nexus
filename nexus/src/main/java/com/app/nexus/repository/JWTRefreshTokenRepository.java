package com.app.nexus.repository;

import com.app.nexus.model.JWTRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTRefreshTokenRepository extends JpaRepository<JWTRefreshToken, String> {

}
