package com.app.nexus.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @Author Amadeus
 * this class is used to keep track of who created or changed an entity and the point of time it happened.'
 * Lets say for example a application user modified/created a task, the task would show who created the task.
 */
@MappedSuperclass
// ignores the created by and updatedby propteries
@JsonIgnoreProperties(value={"createdBy", "updatedBy"}, allowGetters = true)
public abstract class ApplicationUserDateAudit extends DateAudit{
    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
