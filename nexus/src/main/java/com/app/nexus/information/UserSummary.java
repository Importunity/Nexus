package com.app.nexus.information;

import com.app.nexus.model.Project;

/**
 * @Author Amadeus
 */

public class UserSummary {
    private Long id;
    private String username;
    private String first;
    private String last;

    public UserSummary(Long id, String username, String first, String last) {
        this.id = id;
        this.username = username;
        this.first = first;
        this.last = last;
    }

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
}
