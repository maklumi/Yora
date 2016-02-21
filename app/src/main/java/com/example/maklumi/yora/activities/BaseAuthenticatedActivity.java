package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Maklumi on 16-02-16.
 */
public abstract class BaseAuthenticatedActivity extends BaseActivity{
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!application.getAuth().getUser().isLoggedIn()) {

            if (application.getAuth().hasAuthToken()) {
                Intent intent = new Intent(this, AuthenticationActivity.class);
                intent.putExtra(AuthenticationActivity.EXTRA_RETURN_TO_ACTIVITY, getClass().getName());
                startActivity(intent);
            } else {
                startActivity(new Intent(this, LoginActivity.class ));
            }


            finish();
            return;
        }

        onYoraCreate(savedInstanceState);
    }

    protected abstract void onYoraCreate(Bundle savedInstance);
}
