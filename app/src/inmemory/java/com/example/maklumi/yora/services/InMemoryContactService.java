package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.services.entities.ContactRequest;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Maklumi on 21-02-16.
 */
public class InMemoryContactService extends BaseInMemoryService {
    protected InMemoryContactService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void getContactRequests (Contacts.GetContactRequestsRequest request) {
        Contacts.GetContactRequestsResponse response = new Contacts.GetContactRequestsResponse();
        response.Requests =new ArrayList<>();

        for (int i= 0; i < 30; i++){
            response.Requests.add(new ContactRequest(i, request.FromUs, createFakeUser(1, false), new GregorianCalendar()));

        }

        postDelayed(request);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactRequest request){
        Contacts.GetContactResponse response = new Contacts.GetContactResponse();
        response.Contacts = new ArrayList<>();

        for (int i=0; i<3; i++){
            response.Contacts.add(createFakeUser(1, true));

        }

        postDelayed(response);
    }

    @Subscribe
    public void sendContactsRequest (Contacts.SendContactRequestRequest request){
        if (request.UserId == 2){
            Contacts.SendContactRequestResponse response = new Contacts.SendContactRequestResponse();
            response.setOperationError("Something bad happen");
            postDelayed(response);
        } else {
            postDelayed(new Contacts.SendContactRequestResponse());
        }
    }

    @Subscribe
    public void respondToContactRequest(Contacts.RespondToContactRequestRequest request){
        postDelayed(new Contacts.RespondToContactRequestResponse());
    }

    private UserDetails createFakeUser (int id , boolean isContact ){
        String idString = Integer.toString(id);
        return new UserDetails(
                id,
                isContact,
                "Contact " + idString,
                "Contact" + idString,
                "http://www.gravatar.com/avatar/" +idString + "?d=identicon&s=64"
        );
    }
}
