package com.app.nexus.repository;

/**
 * @Author Amadeus
 */

import com.app.nexus.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
