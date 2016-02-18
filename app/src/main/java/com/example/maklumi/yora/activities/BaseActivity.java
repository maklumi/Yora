package com.example.maklumi.yora.activities;
import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.views.NavDrawer;

/**
 * Created by Maklumi on 15-02-16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected YoraApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected boolean isTablet;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        toolbar=(Toolbar)findViewById(R.id.include_toolbar);

        if (toolbar!= null)
            setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YoraApplication) getApplication();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        isTablet = (metrics.widthPixels / metrics.density) >= 600;
    }

    protected void setNavDrawer(NavDrawer drawer){
        this.navDrawer = drawer;
        this.navDrawer.create();

        //disable native animation
        overridePendingTransition(0,0);
        View rootView = findViewById(android.R.id.content);
        rootView.setAlpha(0);
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

    //interface
    public interface FadeOutListener {
        void onFadeOutEnd();
    }


}
