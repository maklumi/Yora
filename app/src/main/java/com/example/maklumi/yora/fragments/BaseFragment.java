package com.example.maklumi.yora.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Button;

import com.example.maklumi.yora.infrastructure.ActionScheduler;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Bus;

/**
 * Created by Maklumi on 16-02-16.
 */
public abstract class BaseFragment extends Fragment {
    protected YoraApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YoraApplication) getActivity().getApplication(); //fragment must go through parent
        scheduler = new ActionScheduler(application);
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }
}
