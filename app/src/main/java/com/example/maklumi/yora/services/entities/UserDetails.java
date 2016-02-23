package com.example.maklumi.yora.services.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maklumi on 21-02-16.
 */
public class UserDetails implements Parcelable{
    private final int id;
    private final boolean isContact;
    private final String displayName;
    private final String username;
    private final String avatarUrl;

    public UserDetails(int id, boolean isContact, String displayName, String username, String avatarUrl) {
        this.id = id;
        this.isContact = isContact;
        this.displayName = displayName;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    private UserDetails(Parcel  parcel){
        id = parcel.readInt();
        isContact = parcel.readByte()== 1;
        displayName = parcel.readString();
        username = parcel.readString();
        avatarUrl = parcel.readString();

    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeInt(id);
        destination.writeByte((byte) (isContact ? 1:0));
        destination.writeString(displayName);
        destination.writeString(username);
        destination.writeString(avatarUrl);
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

    public boolean isContact() {
        return isContact;
    }


    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
}


























