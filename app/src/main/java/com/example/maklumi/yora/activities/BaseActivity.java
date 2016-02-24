package com.example.maklumi.yora.activities;
import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.infrastructure.ActionScheduler;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.views.NavDrawer;
import com.squareup.otto.Bus;

/**
 * Created by Maklumi on 15-02-16.
 */
public abstract class BaseActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private boolean isRegisteredWithBus;

    protected YoraApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected boolean isTablet;
    protected Bus bus;
    protected ActionScheduler scheduler;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YoraApplication) getApplication();
        bus = application.getBus();
        scheduler = new ActionScheduler(application);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        isTablet = (metrics.widthPixels / metrics.density) >= 600;

        bus.register(this);

        isRegisteredWithBus = true;
    }

    public ActionScheduler getScheduler() {
        return scheduler;
    }

    @Override
    protected void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scheduler.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isRegisteredWithBus) {

            bus.unregister(this);
            isRegisteredWithBus = false;
        }
        if (navDrawer != null) navDrawer.destroy();
    }

    @Override
    public void finish() {
        super.finish();

        if (isRegisteredWithBus){
            bus.unregister(this);
            isRegisteredWithBus = false;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        toolbar = (Toolbar)findViewById(R.id.include_toolbar);
        if (toolbar!= null) {
            setSupportActionBar(toolbar);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeColors(
                    Color.parseColor("#ff0000ff"),
                    Color.parseColor("#ff99cc00"),
                    Color.parseColor("#ffff0022"),
                    Color.parseColor("#ffff4444")
            );
        }

    }

    protected void setNavDrawer(NavDrawer drawer){
        this.navDrawer = drawer;
        this.navDrawer.create();

        //disable native animation
        overridePendingTransition(0,0); //(enteranim, exitanim)

        View rootView = findViewById(android.R.id.content);
        rootView.setAlpha(0); //completely transparent, zero opaque
        rootView.animate().alpha(1).setDuration(150).start();
    }

    public Toolbar getToolbar(){
        return  toolbar;
    }

    public YoraApplication getYoraApplication() {
        return application;
    }

    //animation
    public void fadeOut(final FadeOutListener listener  ){
        View rootView = findViewById(android.R.id.content);
        rootView.animate()
                .alpha(0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listener.onFadeOutEnd();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .setDuration(300)
                .start();

    }

    @Override
    public void onRefresh() {

    }

    //interface
    public interface FadeOutListener {
        void onFadeOutEnd();
    }


}
