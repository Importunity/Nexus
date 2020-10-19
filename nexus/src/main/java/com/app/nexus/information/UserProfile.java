package com.app.nexus.information;

import java.time.Instant;

/**
 * @Author Amadeus
 * class is to show the information of the user profile
 * such as what organization the user belongs to and what projects
 * has the user worked on or what projects the user are currently
 * associated with.
 */

public class UserProfile {
    private Long id;
    private String username;
    private String first;
    private String last;
    // date joined at
    private Instant joinedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }
}
