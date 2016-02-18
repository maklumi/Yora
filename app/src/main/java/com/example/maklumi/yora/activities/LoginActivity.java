package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.fragments.LoginFragment;

/**
 * Created by Maklumi on 15-02-16.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener , LoginFragment.Callbacks{
    private static final int REQUEST_REGISTER = 2;
    private static final int REQUEST_NARROW_LOGIN = 1;
    private static final int REQUEST_EXTERNAL_LOGIN = 3;
    private View loginButton;
    private View registerButton;
    private View facebookLoginButton;
    private View googleLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        loginButton =  findViewById(R.id.activity_login_login);
        if(loginButton != null) {
            loginButton.setOnClickListener(this);
        }

        registerButton  = findViewById(R.id.activity_login_register);
        facebookLoginButton = findViewById(R.id.activity_login_facebook);
        googleLoginButton = findViewById(R.id.activity_login_google);
        registerButton.setOnClickListener(this);
        facebookLoginButton.setOnClickListener(this);
        googleLoginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == loginButton)
            startActivityForResult(new Intent(this, LoginNarrowActivity.class), REQUEST_NARROW_LOGIN);
        else if (v == registerButton)
            startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_REGISTER);
        else if (v==facebookLoginButton)
            doExternalLogin("Facebook");
        else if (v==googleLoginButton)
            doExternalLogin("Google");

    }

    private void doExternalLogin(String externalService) {
        Intent intent = new Intent(this, ExternalLoginActivity.class);
        intent.putExtra(ExternalLoginActivity.EXTRA_EXTERNAL_SERVICE, externalService);
        startActivityForResult(intent, REQUEST_EXTERNAL_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;; //eg press back button

        if (requestCode == REQUEST_NARROW_LOGIN ||
                requestCode == REQUEST_REGISTER ||
                requestCode == REQUEST_EXTERNAL_LOGIN)
            finishLogin();
    }

    private void finishLogin() {
        application.getAuth().getUser().setLoggedIn(true); //temp
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoggedIn() {
        finishLogin();
    }
}
