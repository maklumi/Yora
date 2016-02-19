package com.example.maklumi.yora.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.example.maklumi.yora.infrastructure.YoraApplication;

/**
 * Created by HomePC on 19/2/2016.
 */
public class BaseDialogFragment extends DialogFragment {
    protected YoraApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (YoraApplication) getActivity().getApplication();
    }
}
