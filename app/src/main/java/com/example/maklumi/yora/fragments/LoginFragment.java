package com.example.maklumi.yora.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Account;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 16-02-16.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    // show view
    private Button loginButton;
    private Callbacks callbacks;
    private View progressBar;
    private EditText usernameText;
    private EditText passwordText;
    private String defaultLoginButtonText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false); //false else twice attached

        loginButton = (Button) view.findViewById(R.id.fragment_login_loginButton);

        if (loginButton!=null) //
            loginButton.setOnClickListener(this);

        progressBar = view.findViewById(R.id.fragment_login_progress);
        usernameText = (EditText) view.findViewById(R.id.fragment_login_username);
        passwordText = (EditText) view.findViewById(R.id.fragment_login_password);

        defaultLoginButtonText = loginButton.getText().toString();

        return view;


    }


    @Override
    public void onClick(View v) {
        if (v == loginButton){
//            application.getAuth().getUser().setLoggedIn(true);
//            if (callbacks != null)
//            callbacks.onLoggedIn();
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setText("");
            loginButton.setEnabled(false);
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            bus.post(new Account.LoginWithUsernameRequest(
                    usernameText.getText().toString(),
                    passwordText.getText().toString()));
        }
    }

    @Subscribe
    public void onLoginWithUsername(Account.LoginWithUsernameResponse response){
        response.showErrorToast(getActivity());

        if (response.didSucceed()){
            callbacks.onLoggedIn();
            return;
        }

        usernameText.setError(response.getPropertyErrors("userName"));
        usernameText.setEnabled(true);
        passwordText.setError(response.getPropertyErrors("password"));
        passwordText.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        loginButton.setText(defaultLoginButtonText);
        loginButton.setEnabled(true);
    }

    public interface Callbacks {
        void onLoggedIn();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null; // remove fragment life
    }
}
