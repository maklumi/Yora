package com.example.maklumi.yora.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Button;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Bus;

/**
 * Created by Maklumi on 16-02-16.
 */
public abstract class BaseFragment extends Fragment {
    protected YoraApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YoraApplication) getActivity().getApplication(); //fragment must go through parent
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
