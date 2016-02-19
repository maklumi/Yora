package com.example.maklumi.yora.activities;

import android.os.Bundle;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.views.MainNavDrawer;

/**
 * Created by Maklumi on 16-02-16.
 */
public class MainActivity extends BaseAuthenticatedActivity {

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_main);
      //  toolbar.setTitle("Inbox"); <-- no effect
        getSupportActionBar().setTitle("Inbox");

        setNavDrawer(new MainNavDrawer(this));
    }

}
