package com.example.maklumi.yora.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.activities.BaseActivity;
import com.example.maklumi.yora.services.Contacts;
import com.example.maklumi.yora.views.ContactRequestAdapter;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 21-02-16.
 */
public class PendingContactRequestsFragment extends BaseFragment {
    private View progressFrame;
    private ContactRequestAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);

        progressFrame = view.findViewById(R.id.fragment_pending_contact_requests_progressFrame);
        adapter = new ContactRequestAdapter((BaseActivity) getActivity());

        ListView listView = (ListView) view.findViewById(R.id.fragment_pending_contact_requests_list);
        listView.setAdapter(adapter);

        bus.post(new Contacts.GetContactRequestsRequest(true));
        return view;
    }

    @Subscribe
    public void onGetContactRequests(final Contacts.GetContactRequestsResponse response){
        scheduler.invokeOnResume(Contacts.GetContactRequestsResponse.class, new Runnable() {
            @Override
            public void run() {

                progressFrame.animate()
                        .alpha(0)
                        .setDuration(250)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                progressFrame.setVisibility(View.GONE);
                            }
                        })
                        .start();

                if (!response.didSucceed()){
                    response.showErrorToast(getActivity());
                    return;
                }

                adapter.clear();
                adapter.addAll(response.Requests);
            }
        });

    }


}
