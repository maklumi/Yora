package com.example.maklumi.yora.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Account;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 16-02-16.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;
    private View progressBar;
    private String defaultRegisterButtonText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        usernameText = (EditText) findViewById(R.id.activity_register_userName);
        emailText = (EditText) findViewById(R.id.activity_register_email);
        passwordText = (EditText) findViewById(R.id.activity_register_password);
        registerButton = (Button) findViewById(R.id.activity_register_registerButton);
        progressBar = findViewById(R.id.activity_register_progressBar);
        if (registerButton != null)
             registerButton.setOnClickListener(this);
        progressBar.setVisibility(View.GONE);
        defaultRegisterButtonText = registerButton.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v == registerButton) {
//            setResult(RESULT_OK);
//            finish();
            progressBar.setVisibility(View.VISIBLE);
            registerButton.setText("");
            registerButton.setEnabled(false);
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            emailText.setEnabled(false);

            bus.post(new Account.RegisterRequest(
                    usernameText.getText().toString(),
                    passwordText.getText().toString(),
                    emailText.getText().toString()
            ));
        }
    }

    @Subscribe
    public void registerResponse (Account.RegisterResponse response){
        onUserResponse(response);
    }

    @Subscribe
    public void externalRegisterResponse (Account.RegisterWithExternalTokenResponse response){
        onUserResponse(response);
    }

    private void onUserResponse (Account.UserResponse response) {
        if (response.didSucceed()) {
            setResult(RESULT_OK);
            finish();
            return;
        }

        response.showErrorToast(this);
        usernameText.setError(response.getPropertyErrors("userName"));
        passwordText.setError(response.getPropertyErrors("password"));
        emailText.setError(response.getPropertyErrors("email"));
        registerButton.setEnabled(true);
        usernameText.setEnabled(true);
        passwordText.setEnabled(true);
        emailText.setEnabled(true);

        progressBar.setVisibility(View.GONE);
        registerButton.setText(defaultRegisterButtonText);
    }
}
