package com.example.maklumi.yora.services;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Subscribe;

/**
 * Created by HomePC on 20/2/2016.
 */
public class InMemoryAccountService {
    private YoraApplication application;

    public InMemoryAccountService (YoraApplication application)
    {
        application.getBus().register(this);
        this.application = application;
    }

    @Subscribe
    public void updateProfile (Account.UpdateProfileRequest request){
        Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();
        application.getBus().post(response);
    }
}
