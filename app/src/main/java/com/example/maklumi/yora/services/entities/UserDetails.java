package com.example.maklumi.yora.services.entities;

/**
 * Created by Maklumi on 21-02-16.
 */
public class UserDetails {
    private int id;
    private boolean isContatact;
    private String displayName;
    private String username;

    public UserDetails(int id, boolean isContatact, String displayName, String username, String avatarUrl) {
        this.id = id;
        this.isContatact = isContatact;
        this.displayName = displayName;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isContatact() {
        return isContatact;
    }

    public int getId() {
        return id;
    }

    private String avatarUrl;

}
