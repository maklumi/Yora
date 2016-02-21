package com.example.maklumi.yora.infrastructure;


import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HomePC on 21/2/2016.
 */
public class ActionScheduler {

    private final YoraApplication application;
    private final Handler handler;
    private final ArrayList<TimeCallback> timeCallbacks;
    private final HashMap<Class, Runnable> onResumeAction;
    private boolean isPaused;

    public ActionScheduler(YoraApplication application) {

        onResumeAction = new HashMap<>();
        this.application = application;
        handler = new Handler();
        timeCallbacks = new ArrayList<>();
    }

    public void onPause() {
        isPaused = true;
    }

    public void onResume() {
isPaused = false;
        for (TimeCallback callback : timeCallbacks ){
            callback.schedule();
        }

        for (Runnable runnable : onResumeAction.values()) {
            runnable.run();
        }

        onResumeAction.clear();
    }

    public void invokeOnResume(Class cls, Runnable runnable) {
        if (!isPaused) {
            runnable.run();
            return;
        }

        onResumeAction.put(cls, runnable);
    }

    public void postDelayed(Runnable runnable, long milliseconds) {
        handler.postDelayed(runnable, milliseconds);
    }

    public void invokeEveryMilliseconds(Runnable runnable, long milliseconds) {
        invokeEveryMilliseconds(runnable, milliseconds, true);
    }

    public void invokeEveryMilliseconds(Runnable runnable, long milliseconds, boolean runImmediately) {
        TimeCallback callback = new TimeCallback(runnable, milliseconds);
        timeCallbacks.add(callback);

        if (runImmediately) {
            callback.run();
        } else {
            postDelayed(callback, milliseconds);
        }
    }

    public void postEveryMilliseconds(Object request, long milliseconds) {
        postEveryMilliseconds(request, milliseconds, true);
    }

    public void postEveryMilliseconds(final Object request, long milliseconds, boolean postImmediately) {
        invokeEveryMilliseconds(new Runnable() {
            @Override
            public void run() {
                application.getBus().post(request);
            }
        }, milliseconds, postImmediately);
    }

    private class TimeCallback implements Runnable {
        private final Runnable runnable;
        private final long delay;

        public TimeCallback(Runnable runnable, long delay) {
            this.runnable = runnable;
            this.delay = delay;
        }

        @Override
        public void run() {
            if (isPaused)
                return;;

            runnable.run();
            schedule();
        }

        public void schedule() {
            handler.postDelayed(this, delay);
        }
    }
}

