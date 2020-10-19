package com.app.nexus.repository;

/**
 * @Author Amadeus
 */

import com.app.nexus.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
