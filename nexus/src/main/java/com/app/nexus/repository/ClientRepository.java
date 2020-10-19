package com.app.nexus.repository;

/**
 * @Author Amadeus
 */

import com.app.nexus.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long> {
}
