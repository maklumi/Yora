package com.example.maklumi.yora.infrastructure;

import android.content.Context;

/**
 * Created by Maklumi on 15-02-16.
 */
public class Auth
{
    private final Context context;
    private User user;

    public Auth(Context context) {
        user = new User();
        this.context = context;
    }

    public User getUser() {
        return user;
    }
}
