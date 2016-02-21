package com.example.maklumi.yora.activities;

import android.os.Bundle;

import com.example.maklumi.yora.R;

/**
 * Created by Maklumi on 21-02-16.
 */
public class AddContactActivity extends BaseAuthenticatedActivity {
    public static final String RESULT_CONTACT = "RESULT_CONTACT";
    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_add_contact);
    }
}
