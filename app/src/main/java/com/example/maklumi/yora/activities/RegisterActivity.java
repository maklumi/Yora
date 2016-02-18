package com.example.maklumi.yora.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.maklumi.yora.R;

/**
 * Created by Maklumi on 16-02-16.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;
    private View progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        usernameText.findViewById(R.id.activity_register_userName);
        emailText.findViewById(R.id.activity_register_email);
        passwordText.findViewById(R.id.activity_register_password);
        registerButton.findViewById(R.id.activity_register_registerButton);
        progressBar.findViewById(R.id.activity_register_progressBar);

        registerButton.setOnClickListener(this);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == registerButton) {
            application.getAuth().getUser().setLoggedIn(true);// temp
            setResult(RESULT_OK);
            finish();
        }
    }
}
