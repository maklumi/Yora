package com.example.maklumi.yora.activities;

import android.os.Bundle;

import com.example.maklumi.yora.R;

/**
 * Created by Maklumi on 21-02-16.
 */
public class ContactActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_USER_DETAILS = "EXTRA_USER_DETAILS";

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_contact);
    }
}
