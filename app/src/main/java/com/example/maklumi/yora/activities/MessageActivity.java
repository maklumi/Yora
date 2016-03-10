package com.example.maklumi.yora.activities;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Messages;
import com.example.maklumi.yora.services.entities.Message;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.otto.Subscribe;

import java.util.GregorianCalendar;

/**
 * Created by Maklumi on 23-02-16.
 */
public class MessageActivity extends BaseAuthenticatedActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final int REQUEST_IMAGE_DELETED = 100;
    public static final String RESULT_EXTRA_MESSAGE_ID = "RESULT_EXTRA_MESSAGE_ID";
    public static final String EXTRA_MESSAGE_ID = "EXTRA_MESSAGE_ID";
    public static final String STATE_MESSAGE = "STATE_MESSAGE";

    private View drawer;
    private TextView translateButton;
    private boolean isOpen;
    private int translateOffset;
    private AnimatorSet currentAnimation;
    private Message currentMessage;
    private View progressFrame;
    private TextView shortMessage;
    private TextView longMessage;

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_messsage);

        drawer = findViewById(R.id.activity_message_drawer);

        translateButton = (TextView) findViewById(R.id.activity_message_translate);
        progressFrame = findViewById(R.id.activity_message_progressFrame);
        shortMessage = (TextView) findViewById(R.id.activity_message_shortMessage);
        longMessage = (TextView) findViewById(R.id.activity_message_longMessage);

        drawer.setOnClickListener(this);

        shortMessage.setText("");
        longMessage.setText("");
        progressFrame.setVisibility(View.GONE);

        toolbar.setNavigationIcon(R.drawable.ic_check_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMessage(RESULT_OK);
            }
        });

        Message message = null;
        if (savedInstance != null){
            message = savedInstance.getParcelable(STATE_MESSAGE);
        }

        if (message == null){
            message = getIntent().getParcelableExtra(EXTRA_MESSAGE);
            if (message == null){
                int id = getIntent().getIntExtra(EXTRA_MESSAGE_ID, -1);
                if (id != -1 ){
                    progressFrame.setVisibility(View.VISIBLE);
                    bus.post(new Messages.GetMessageDetailRequest(id));
                } else {
                    message = new Message(
                            1,
                            new GregorianCalendar(),
                            "SHORT MESSAGE",
                            "LONG MESSAGE",
                            null,
                            new UserDetails(1, true, "Person","Person",""),
                            false,
                            false
                    );
                }
            }
        }

        if (message != null){
            showMessage(message);
        }
    }

    private void closeMessage(int resultCode) {
        Intent data = new Intent();
        data.putExtra(RESULT_EXTRA_MESSAGE_ID, currentMessage.getId());
        setResult(resultCode, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activity_message_menuContact){
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra(ContactActivity.EXTRA_USER_DETAILS, currentMessage.getOtherUser());
            startActivity(intent);
            return true;
        } else if (id == R.id.activity_message_menuReplay){
            Intent intent = new Intent(this, NewMessageActivity.class);
            intent.putExtra(NewMessageActivity.EXTRA_CONTACT, currentMessage.getOtherUser());
            startActivity(intent);
            return true;
        } else if (id == R.id.activity_message_menuDelete){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete Message")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bus.post(new Messages.DeleteMessageRequest(currentMessage.getId()));
                            closeMessage(REQUEST_IMAGE_DELETED);
                        }
                    })
                    .setCancelable(false)
                    .setNeutralButton("Cancel", null)
                    .create();

            dialog.show();
        }

        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.activity_message_menuReplay).setVisible(
                currentMessage!=null && !currentMessage.isFromUs());
        return true;
    }

    @Subscribe
    public void onMessageDetailsLoaded (final Messages.GetMessageDetailResponse response){
        scheduler.invokeOnResume(response.getClass(), new Runnable() {
            @Override
            public void run() {
                progressFrame.setVisibility(View.GONE);

                if (!response.didSucceed()){
                    response.showErrorToast(MessageActivity.this);
                    return;
                }

                showMessage(response.Message);
            }
        });
    }

    private void showMessage(Message message) {
        currentMessage = message;

        String createdAt = DateUtils.formatDateTime(this, message.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE);

        if (message.isFromUs()) {
            getSupportActionBar().setTitle("Sent on " + createdAt);
        } else {
            getSupportActionBar().setTitle("Received on " + createdAt);
        }

        longMessage.setText(message.getLongMessage());
        shortMessage.setText(message.getShortMessage());

        if (message.getImageUrl() != null && !message.getImageUrl().isEmpty()) {
            //TODO loadn image
        }

        invalidateOptionsMenu();
        Intent defaultResult = new Intent();
        defaultResult.putExtra(RESULT_EXTRA_MESSAGE_ID, message.getId());
        setResult(RESULT_OK, defaultResult);

        if (!message.isRead())
        bus.post(new Messages.MarkMessageAsReadRequest(message.getId()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_MESSAGE, currentMessage);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

            translateOffset = drawer.getHeight() - translateButton.getHeight();
            drawer.setTranslationY(translateOffset);

    }

    @Override
    public void onClick(View v) {
        isOpen = !isOpen;

        if (currentAnimation != null){
            currentAnimation.cancel();
        }

        int currentBackgroundColor = ((ColorDrawable ) drawer.getBackground()).getColor();
        int translationY, color;

        if (!isOpen){
            translationY = 0;
            color = Color.parseColor("#EE1990FC");
            translateButton.setText("Close");

        } else {
            translationY = translateOffset;
            color = Color.parseColor("#221990FC");
            translateButton.setText("Translate");
        }

        ObjectAnimator translateAnimator = ObjectAnimator
                .ofFloat(drawer, "translationY", translationY)
                .setDuration(100);

        ObjectAnimator colorAnimator = ObjectAnimator
                .ofInt(drawer, "backgroundColor", currentBackgroundColor, color)
                .setDuration(100);

        colorAnimator.setEvaluator(new ArgbEvaluator());

        currentAnimation = new AnimatorSet();
        currentAnimation.setDuration(400);
        currentAnimation.play(translateAnimator).with(colorAnimator);
        currentAnimation.start();
    }
}
















