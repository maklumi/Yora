package com.example.maklumi.yora.services.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Maklumi on 23-02-16.
 */
public class Message implements Parcelable{
    private int id;
    private Calendar createdAt;
    private String shortMessage;
    private String longMessage;
    private String imageUrl;
    private UserDetails otherUser;
    private boolean isFromUs;
    private boolean isRead;
    private boolean isSelected;

    public Message(int id, Calendar createdAt, String shortMessage, String longMessage,
                   String imageUrl, UserDetails otherUser,
                   boolean isFromUs, boolean isRead) {
        this.id = id;
        this.createdAt = createdAt;
        this.shortMessage = shortMessage;
        this.longMessage = longMessage;
        this.imageUrl = imageUrl;
        this.otherUser = otherUser;
        this.isFromUs = isFromUs;
        this.isRead = isRead;
    }

    private Message(Parcel in){
        id = in.readInt();
        createdAt = new GregorianCalendar();
        createdAt.setTimeInMillis(in.readLong());
        shortMessage = in.readString();
        longMessage = in.readString();
        imageUrl = in.readString();
        otherUser = (UserDetails) in.readParcelable(UserDetails.class.getClassLoader());
        isFromUs = in.readByte() == 1;
        isRead = in.readByte() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(createdAt.getTimeInMillis());
        dest.writeString(shortMessage);
        dest.writeString(longMessage);
        dest.writeString(imageUrl);
        dest.writeParcelable(otherUser, 0);
        dest.writeByte((byte) (isFromUs ? 1:0));
        dest.writeByte((byte) (isRead ? 1:0));
    }

    public int getId() {
        return id;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserDetails getOtherUser() {
        return otherUser;
    }

    public boolean isFromUs() {
        return isFromUs;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[0];
        }
    };
}
