package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.ServiceResponse;
import com.example.maklumi.yora.services.entities.Message;

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
        public String FromContactId;
        public boolean IncludeSentMessages;
        public boolean IncludeReceivedMessages;

        public SearchMessagesRequest(boolean includeSentMessages, boolean includeReceivedMessages) {
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }
    }

    public static class SearchMessagesResponse extends ServiceResponse{
        public List<Message> Messages;
    }
}
