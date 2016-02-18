package com.example.maklumi.yora.infrastructure;

import android.app.Application;

/**
 * Created by Maklumi on 15-02-16.
 */
public class YoraApplication extends Application{
    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
    }
}
