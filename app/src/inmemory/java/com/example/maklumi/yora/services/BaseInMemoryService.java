package com.example.maklumi.yora.services;

import android.os.Handler;

import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.squareup.otto.Bus;

import java.util.Random;

/**
 * Created by Maklumi on 20-02-16.
 */
public abstract class BaseInMemoryService {
    protected final Bus bus;
    protected final YoraApplication application;
    protected final Handler handler;
    protected final Random random;


    protected BaseInMemoryService(YoraApplication application) {
        this.application = application;
        bus = application.getBus();
        handler = new Handler();
        random = new Random();
        bus.register(this);
    }

    protected void invokeDelayed(Runnable runnable, long milisecondMin, long milisecondMax){
        if (milisecondMin > milisecondMax)
            throw new IllegalArgumentException("Minimum must be smaller than maximum");

        long delay = (long) ((milisecondMax-milisecondMin) * random.nextDouble()) + milisecondMin;
        handler.postDelayed(runnable, delay);
    }

    protected void postDelayed(final Object event, long milisecondMin, long milisecondMax){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(event);
            }
        }, milisecondMin, milisecondMax);
    }

    protected void postDelayed(Object event, long miliseconds){
        postDelayed(event, miliseconds, miliseconds);
    }

    protected void postDelayed(Object event) {
        postDelayed(event, 600,1200);
    }

}
