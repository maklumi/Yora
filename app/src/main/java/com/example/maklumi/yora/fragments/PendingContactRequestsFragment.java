package com.example.maklumi.yora.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maklumi.yora.R;

/**
 * Created by Maklumi on 21-02-16.
 */
public class PendingContactRequestsFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);

        return view;
    }
}
