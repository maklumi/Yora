package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.services.entities.ContactRequest;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class InMemoryContactsService extends BaseInMemoryService {
    public InMemoryContactsService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void getContactRequests(Contacts.GetContactRequestsRequest request) {
        Contacts.GetContactRequestsResponse response = new Contacts.GetContactRequestsResponse();
        response.Requests = new ArrayList<>();

        for (int i = 0;  i < 3; i++) {
            response.Requests.add(new ContactRequest(request.FromUs, createFakeUser(i, false), new GregorianCalendar()));
        }

        postDelayed(response);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request) {
        Contacts.GetContactsResponse response = new Contacts.GetContactsResponse();
        response.Contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            response.Contacts.add(createFakeUser(i, true));
        }

        postDelayed(response, 5000);
    }

    @Subscribe
    public void sendContactsRequest(Contacts.SendContactRequestRequest request) {
        if (request.UserId == 2) {
            Contacts.SendContactRequestResponse response = new Contacts.SendContactRequestResponse();
            response.setOperationError("Something bad happened!");
            postDelayed(response);
        } else {
            postDelayed(new Contacts.SendContactRequestResponse());
        }
    }

    @Subscribe
    public void respondToContactsRequest(Contacts.RespondToContactRequestRequest request) {
        postDelayed(new Contacts.RespondToContactRequestResponse());
    }

    @Subscribe
    public void removeContact(Contacts.RemoveContactRequest request) {
        Contacts.RemoveContactResponse response = new Contacts.RemoveContactResponse();
        response.RemovedContactId = request.ContactId;
        postDelayed(response);
    }

    @Subscribe
    public void searchUsers(Contacts.SearchUsersRequest request) {
        Contacts.SearchUsersResponse response = new Contacts.SearchUsersResponse();
        response.Query = request.Query;
        response.Users = new ArrayList<>();

        for (int i = 0; i < request.Query.length(); i++) {
            response.Users.add(createFakeUser(i, false));
        }

        postDelayed(response, 2000, 3000);
    }

    private UserDetails createFakeUser(int id, boolean isContact) {
        String idString = Integer.toString(id);
        return new UserDetails(
                id,
                isContact,
                "Contact " + idString,
                "Contact" + idString,
                "http://www.gravatar.com/avatar/" + idString + "?d=identicon&s=64");
    }
}
