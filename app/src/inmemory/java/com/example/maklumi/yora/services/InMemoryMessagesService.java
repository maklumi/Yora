package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.services.entities.Message;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Maklumi on 23-02-16.
 */
public class InMemoryMessagesService extends BaseInMemoryService {
    public InMemoryMessagesService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void deleteMessage(Messages.DeleteMessageRequest request) {
        Messages.DeleteMessageResponse response = new Messages.DeleteMessageResponse();
        response.MessageId = request.MessageId;
        postDelayed(response);
    }

    @Subscribe
    public void searchMessages(Messages.SearchMessagesRequest request){
        Messages.SearchMessagesResponse response = new Messages.SearchMessagesResponse();
        response.Messages = new ArrayList<>();

        UserDetails [] users = new UserDetails[10];
        for (int i = 0; i < users.length; i++)
        {
            String stringId = Integer.toString(i);
            users[i] = new UserDetails(i, true, "User " + stringId, "user " + stringId,
                    "http://www.gravatar.com/avatar/"+ stringId + "?d=identicon");
        }

        Random random = new Random();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -100);

        for (int i=0; i < 100; i++){
            boolean isFromUs;

            if (request.IncludeReceivedMessages && request.IncludeSentMessages) {
                isFromUs = random.nextBoolean();
            } else {
                isFromUs = !request.IncludeReceivedMessages;
            }

            date.set(Calendar.MINUTE, random.nextInt(60 * 24));

            String numberString = Integer.toString(i);
            response.Messages.add(new Message(
                    i,
                    (Calendar) date.clone(),
                    "Short message " + numberString,
                    "Long message " + numberString,
                    "",
                    users[random.nextInt(users.length)],
                    isFromUs,
                    i>4
            ));
        }

        postDelayed(response, 2000);
    }

    @Subscribe
    public void sendMessage(Messages.SendMessageRequest request){
        Messages.SendMessageResponse res = new Messages.SendMessageResponse();

        if (request.getMessage().equals("error")) {
            res.setOperationError("Error sending message");

        } else if (request.getMessage().equals("error-message")) {
            res.setPropertyErrors("message", "Invalid message");
        }

        postDelayed(res, 1500, 3000);
    }

}
