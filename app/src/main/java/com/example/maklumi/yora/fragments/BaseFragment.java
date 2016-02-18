package com.example.maklumi.yora.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.example.maklumi.yora.infrastructure.YoraApplication;

/**
 * Created by Maklumi on 16-02-16.
 */
public abstract class BaseFragment extends Fragment {
    protected YoraApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YoraApplication) getActivity().getApplication(); //fragment must go through parent

    }
}
