package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.ServiceResponse;
import com.example.maklumi.yora.services.entities.ContactRequest;
import com.example.maklumi.yora.services.entities.UserDetails;

import java.util.List;

/**
 * Created by Maklumi on 21-02-16.
 */
public final class Contacts {
    private Contacts() {

    }

    public static class GetContactRequestsRequest {
        public boolean FromUs;

        public GetContactRequestsRequest(boolean fromUs) {
            FromUs = fromUs;
        }
    }

    public static class GetContactRequestsResponse extends ServiceResponse {
        public List<ContactRequest> Requests;
    }

    public static class GetContactRequest {
        public boolean IncludePendingContacts;

        public GetContactRequest(boolean includePendingContacts) {
            IncludePendingContacts = includePendingContacts;
        }
    }

    public static class GetContactResponse extends ServiceResponse {
        public List<UserDetails> Contacts;
    }

    public static class SendContactRequestRequest {
        public int UserId;

        public SendContactRequestRequest(int userId) {
            UserId = userId;
        }
    }

    public static class SendContactRequestResponse extends ServiceResponse {
    }

    public static class RespondToContactRequestRequest {
        public int ContactRequestId;
        public boolean Accept;

        public RespondToContactRequestRequest(int contactRequestId, boolean accept) {
            ContactRequestId = contactRequestId;
            Accept = accept;
        }
    }

    public static class RespondToContactRequestResponse extends ServiceResponse {

    }
}
