package com.example.maklumi.yora.activities;

import android.os.Bundle;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.views.MainNavDrawer;

/**
 * Created by Maklumi on 17-02-16.
 */
public class SendMessagesActivity extends BaseAuthenticatedActivity {
    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_main_messages);
        setNavDrawer(new MainNavDrawer(this));
    }
}
