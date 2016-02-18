package com.example.maklumi.yora.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.fragments.LoginFragment;

/**
 * Created by Maklumi on 16-02-16.
 */
public class LoginNarrowActivity extends BaseActivity implements LoginFragment.Callbacks {
    private View loginNarrowButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_narrow);

        loginNarrowButton = findViewById(R.id.activity_login_narrow_fragment);
    }

    @Override
    public void onLoggedIn() {
        setResult(RESULT_OK);
        finish();
    }
}
