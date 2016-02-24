package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Contacts;
import com.example.maklumi.yora.services.Messages;
import com.example.maklumi.yora.services.entities.ContactRequest;
import com.example.maklumi.yora.services.entities.Message;
import com.example.maklumi.yora.views.MainActivityAdapter;
import com.example.maklumi.yora.views.MainNavDrawer;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by Maklumi on 16-02-16.
 */
public class MainActivity extends BaseAuthenticatedActivity implements View.OnClickListener, MainActivityAdapter.MainActivityListener {

    private MainActivityAdapter adapter;
    private List<Message> messages;
    private List<ContactRequest> contactRequests;

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Inbox");
        setNavDrawer(new MainNavDrawer(this));

        findViewById(R.id.activity_main_menuMessageButton).setOnClickListener(this);

        adapter = new MainActivityAdapter(this, this);
        messages = adapter.getMessages();
        contactRequests = adapter.getContactRequests();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        recyclerView.setAdapter(adapter);

        if (isTablet) {
            GridLayoutManager manager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(manager);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 2;
                    }

                    if (contactRequests.size() > 0 && position == contactRequests.size() + 1 ) {
                        return 2;
                    }

                    return 1;
                }
            });
        } else {

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        scheduler.invokeEveryMilliseconds(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 1000 * 30 * 2);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.activity_main_menuMessageButton){
            startActivity(new Intent(this, NewMessageActivity.class));
        }
    }

    @Override
    public void onMessageClicked(Message message) {

    }

    @Override
    public void onContactRequestClicked(ContactRequest request, int position) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        bus.post(new Messages.SearchMessagesRequest(false,true));
        bus.post(new Contacts.GetContactRequestsRequest(false));
    }

    @Subscribe
    public void onMessagesLoaded(final Messages.SearchMessagesResponse response){
        scheduler.invokeOnResume(response.getClass(), new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);

                if (!response.didSucceed()){
                    response.showErrorToast(MainActivity.this);
                    return;
                }

                messages.clear();
                messages.addAll(response.Messages);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe
    public void onContactRequestsLoaded(final Contacts.GetContactRequestsResponse response){
        scheduler.invokeOnResume(response.getClass(), new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);

                if (!response.didSucceed()){
                    response.showErrorToast(MainActivity.this);
                    return;
                }

                contactRequests.clear();
                contactRequests.addAll(response.Requests);
            }
        });
    }
}
