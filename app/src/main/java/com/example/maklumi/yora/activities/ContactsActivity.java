package com.example.maklumi.yora.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.fragments.ContactsFragment;
import com.example.maklumi.yora.fragments.PendingContactRequestsFragment;
import com.example.maklumi.yora.views.MainNavDrawer;

/**
 * Created by Maklumi on 17-02-16.
 */
public class ContactsActivity extends BaseAuthenticatedActivity implements AdapterView.OnItemSelectedListener {
    private ObjectAnimator currentAnimator;
    private ArrayAdapter<ContactsSpinnerItem> adapter;

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_contacts);
        setNavDrawer(new MainNavDrawer(this));

        adapter = new ArrayAdapter<>(this, R.layout.list_item_toolbar_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        adapter.add(new ContactsSpinnerItem("Contacts", Color.parseColor("#0CBCD4"), ContactsFragment.class));
        adapter.add(new ContactsSpinnerItem("Pending Contact Requests",
                getResources().getColor(R.color.contacts_pending_contact_request), PendingContactRequestsFragment.class));

        Spinner spinner = (Spinner) findViewById(R.id.activity_contacts_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setTitle(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ContactsSpinnerItem item = adapter.getItem(position);
        if (item == null)
            return;

        if (currentAnimator != null)
            currentAnimator.end();

        int currentColor = ((ColorDrawable) toolbar.getBackground()).getColor();

        currentAnimator = ObjectAnimator
                .ofObject(toolbar, "backgroundColor", new ArgbEvaluator(), currentColor, item.getColor())
                .setDuration(250);
        currentAnimator.start();

        Fragment fragment;
        try {
            fragment = (Fragment) item.getFragment().newInstance();
        } catch (Exception e) {
           // e.printStackTrace();
            Log.e("ContactsActivity", "Could not instantiate fragment " + item.getFragment().getName(), e);
            return;
        }

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.activity_contacts_fragment_container, fragment)
                .commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ContactsSpinnerItem {
        private final String title;
        private final int color;
        private Class fragment;

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return getTitle();
        }

        public int getColor() {
            return color;
        }

        public Class getFragment() {
            return fragment;
        }

        public ContactsSpinnerItem(String title, int color, Class fragment) {
            this.title = title;
            this.color = color;
            this.fragment = fragment;
        }

    }
}
