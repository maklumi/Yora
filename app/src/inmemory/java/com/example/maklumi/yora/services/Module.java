package com.example.maklumi.yora.services;

import android.util.Log;

import com.example.maklumi.yora.infrastructure.YoraApplication;

/**
 * Created by HomePC on 20/2/2016.
 */
public class Module {
    public static void register(YoraApplication application)
    {
        new InMemoryAccountService(application);
        new InMemoryContactService(application);
        new InMemoryMessagesService(application);
    }
}
