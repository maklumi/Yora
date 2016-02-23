package com.example.maklumi.yora.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Contacts;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.example.maklumi.yora.views.UserDetailsAdapter;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 21-02-16.
 */
public class AddContactActivity extends BaseAuthenticatedActivity implements AdapterView.OnItemClickListener {
    public static final String RESULT_CONTACT = "RESULT_CONTACT";

    private UserDetailsAdapter adapter;
    private View progressFrame;
    private Handler handler;
    private SearchView searchView;
    private String lastQuery;
    private UserDetails selectedUser;

    private Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            lastQuery = searchView.getQuery().toString();
            bus.post(new Contacts.SearchUserRequest(lastQuery));
        }
    };



    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_add_contact);

        adapter = new UserDetailsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.activity_add_contact_userListView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        progressFrame = findViewById(R.id.activity_add_contact_progressFrame);
        progressFrame.setVisibility(View.GONE);

        handler = new Handler();
        searchView = new SearchView(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(searchView);

        searchView.setIconified(false);
        searchView.setQueryHint("Search for user");
        searchView.setLayoutParams(new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT
        ));

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() < 3)
                return true;

                handler.removeCallbacks(searchRunnable);
                handler.postDelayed(searchRunnable, 750);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }
        });

    }

    @Subscribe
    public void onUserSearched(Contacts.SearchUserResponse response) {
        progressFrame.setVisibility(View.GONE);

        if (!response.didSucceed()){
            response.showErrorToast(this);
            return;
        }

        if (!response.Query.equals(lastQuery)) {
            return;
        }

        adapter.clear();
        adapter.addAll(response.Users);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Send Contact Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendContactRequest(adapter.getItem(position));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    private void sendContactRequest(UserDetails user) {

        selectedUser = user;
        progressFrame.setVisibility(View.VISIBLE);
        bus.post(new Contacts.SendContactRequestRequest(user.getId()));
    }

    @Subscribe
    public void onContactRequestSent (Contacts.SendContactRequestResponse response){
        if (!response.didSucceed()) {
            response.showErrorToast(this);
            progressFrame.setVisibility(View.GONE);
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(RESULT_CONTACT, selectedUser);
        setResult(RESULT_OK, intent);

        finish();
    }
}
