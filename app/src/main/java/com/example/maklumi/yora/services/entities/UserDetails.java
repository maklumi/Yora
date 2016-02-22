package com.example.maklumi.yora.services.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maklumi on 21-02-16.
 */
public class UserDetails implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(0, false, null, null, null);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[0];
        }
    };
}
