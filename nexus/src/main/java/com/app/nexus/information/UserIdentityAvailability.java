package com.app.nexus.information;

/**
 * @Author Amadeus
 */

public class UserIdentityAvailability {
    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }


    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
