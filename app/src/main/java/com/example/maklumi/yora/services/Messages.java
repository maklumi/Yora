package com.example.maklumi.yora.services;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.maklumi.yora.infrastructure.ServiceResponse;
import com.example.maklumi.yora.services.entities.Message;
import com.example.maklumi.yora.services.entities.UserDetails;

import java.util.List;

/**
 * Created by Maklumi on 23-02-16.
 */
public final class Messages {
    private Messages() {

    }

    public static class DeleteMessageRequest {
        public  int MessageId;

        public DeleteMessageRequest(int messageId){
            MessageId = messageId;
        }
    }

    public static class DeleteMessageResponse extends ServiceResponse {
        public int MessageId;
    }

    public static class SearchMessagesRequest {
        public int FromContactId;
        public boolean IncludeSentMessages;
        public boolean IncludeReceivedMessages;

        public SearchMessagesRequest(int fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            FromContactId = fromContactId;
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }

        public SearchMessagesRequest( boolean includeSentMessages, boolean includeReceivedMessages) {

            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }

    }

    public static class SearchMessagesResponse extends ServiceResponse{
        public List<Message> Messages;
    }

    public static class SendMessageRequest implements Parcelable {

        private UserDetails recipient;
        private Uri imagePath;
        private String message;

        public SendMessageRequest() {

        }

        private SendMessageRequest(Parcel in) {
            recipient = in.readParcelable(UserDetails.class.getClassLoader());
            imagePath = in.readParcelable(Uri.class.getClassLoader());
            message = in.readString();
        }

        public static Creator<SendMessageRequest> CREATOR = new Creator<SendMessageRequest>() {
            @Override
            public SendMessageRequest createFromParcel(Parcel source) {
                return new SendMessageRequest(source);
            }

            @Override
            public SendMessageRequest[] newArray(int size) {
                return new SendMessageRequest[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeParcelable(recipient, 0);
            dest.writeParcelable(imagePath, 0);
            dest.writeString(message);
        }

        public UserDetails getRecipient() {
            return recipient;
        }

        public void setRecipient(UserDetails recipient) {
            this.recipient = recipient;
        }

        public Uri getImagePath() {
            return imagePath;
        }

        public void setImagePath(Uri imagePath) {
            this.imagePath = imagePath;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class SendMessageResponse extends ServiceResponse {

    }
}
