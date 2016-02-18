package com.example.maklumi.yora.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maklumi.yora.R;

/**
 * Created by Maklumi on 16-02-16.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    // show view
private Button loginButton;
    private Callbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false); //false else twice attached

        loginButton = (Button) view.findViewById(R.id.fragment_login_loginButton);

        if (loginButton!=null)
            loginButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == loginButton){
            application.getAuth().getUser().setLoggedIn(true); //temporary
            callbacks.onLoggedIn();
        }
    }

    public interface Callbacks {
        void onLoggedIn();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null; // remove fragment life
    }
}
