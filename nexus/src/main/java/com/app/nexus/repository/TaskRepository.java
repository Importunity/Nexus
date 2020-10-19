package com.app.nexus.repository;

/**
 * @Author Amadeus
 */

import com.app.nexus.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long taskId);
    //Page<Task> findByCreatedBy(Long userId, Pageable pageable);
    //long countByCreatedBy(Long userId);
    List<Task> findByIdIn(List<Long> taskIds);
    List<Task> findByIdIn(List<Long> taskIds, Sort sort);
}
