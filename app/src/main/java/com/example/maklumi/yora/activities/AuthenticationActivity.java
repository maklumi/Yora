package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.infrastructure.Auth;
import com.example.maklumi.yora.services.Account;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 20-02-16.
 */
public class AuthenticationActivity extends BaseActivity {

    private Auth auth;

    public static final String EXTRA_RETURN_TO_ACTIVITY = "EXTRA_RETURN_TO_ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aunthentication);

        auth = application.getAuth();
        if (!auth.hasAuthToken()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        bus.post(new Account.LoginWithLocalTokenRequest (auth.getAuthToken()));
    }

    @Subscribe
    public void onLoginWithLocalToken (Account.LoginWIthLocalTokenResponse response){
        if (!response.didSucceed()) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            auth.setAuthToken(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        Intent intent;
        String returnTo = getIntent().getStringExtra(EXTRA_RETURN_TO_ACTIVITY);
        if (returnTo != null) {
            try {
                intent = new Intent(this, Class.forName(returnTo));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                intent = new Intent(this, MainActivity.class);
            }
        } else {
            intent = new Intent(this, MainActivity.class);

        }

        startActivity(intent);
        finish();
    }
}
