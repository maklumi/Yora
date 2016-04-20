package com.example.maklumi.yora.services;

import android.util.Log;

import com.example.maklumi.yora.infrastructure.YoraApplication;

public class Module {
    public static void register(YoraApplication application) {
        new InMemoryAccountService(application);
        new InMemoryContactsService(application);
        new InMemoryMessagesService(application);
    }
}