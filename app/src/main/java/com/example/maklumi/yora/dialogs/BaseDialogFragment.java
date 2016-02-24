package com.example.maklumi.yora.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.example.maklumi.yora.infrastructure.ActionScheduler;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Bus;

/**
 * Created by HomePC on 19/2/2016.
 */
public class BaseDialogFragment extends DialogFragment {
    protected YoraApplication application;
    protected Bus bus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (YoraApplication) getActivity().getApplication();
        scheduler = new ActionScheduler(application);
        bus = application.getBus();

        scheduler = new ActionScheduler(application);
        bus.register(this);
    }

    protected ActionScheduler scheduler;

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
