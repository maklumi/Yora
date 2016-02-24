package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Messages;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

/**
 * Created by Maklumi on 24-02-16.
 */
public class SendMessageActivity extends BaseAuthenticatedActivity implements View.OnClickListener {
    public static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";

    public static final int MAX_IMAGE_HEIGHT = 1000;
    private static final String STATE_REQUEST = "STATE_REQUEST";
    private static final int REQUEST_SELECT_RECIPIENT = 1;

    private Messages.SendMessageRequest request;
    private EditText messageEditText;
    private Button recipientButton;
    private View progressFrame;

    @Override
    protected void onYoraCreate(Bundle savedInstance) {
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setTitle("Send Message");

        if (savedInstance != null) {
            request = savedInstance.getParcelable(STATE_REQUEST);
        }

        if (request == null) {
            request = new Messages.SendMessageRequest();
            request.setRecipient((UserDetails) getIntent().getParcelableExtra(EXTRA_CONTACT));
        }

        Uri imageUri = getIntent().getParcelableExtra(EXTRA_IMAGE_PATH);
        if (imageUri != null){
            ImageView imageView = (ImageView) findViewById(R.id.activity_send_message_image);
            Picasso picasso = Picasso.with(this);
            picasso.invalidate(imageUri);
            picasso.load(imageUri).into(imageView);

            request.setImagePath(imageUri);
        }

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            View optionsFrame = findViewById(R.id.activity_send_message_optionFrame);

            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) optionsFrame.getLayoutParams();

            params.addRule(RelativeLayout.ALIGN_END);
            params.addRule(RelativeLayout.BELOW, R.id.include_toolbar);
            params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    100, getResources().getDisplayMetrics());
            optionsFrame.setLayoutParams(params);
        }

        progressFrame = findViewById(R.id.activity_send_message_progressFrame);
        recipientButton = (Button) findViewById(R.id.activity_send_message_recipient);
        messageEditText = (EditText) findViewById(R.id.activity_send_message_message);

        progressFrame.setVisibility(View.GONE);

        recipientButton.setOnClickListener(this);
        updateButton();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_REQUEST, request);
    }

    @Override
    public void onClick(View v) {
        if (v == recipientButton){
            selectRecipient();
        }
    }

    private void updateButton() {
        UserDetails recipient = request.getRecipient();
        if (recipient != null){
            recipientButton.setText("To: " + recipient.getDisplayName());
        } else {
            recipientButton.setText("Choose Recipient");
        }
    }

    private void selectRecipient() {
        startActivityForResult(new Intent(this, SelectContactActivity.class), REQUEST_SELECT_RECIPIENT );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_RECIPIENT && resultCode == RESULT_OK){
            UserDetails selectedContact = data.getParcelableExtra(SelectContactActivity.RESULT_CONTACT);
            request.setRecipient(selectedContact);
            updateButton();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_send_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activity_send_message_menuSend){
            sendMessage();
            return true;
        }
        return false;

    }

    private void sendMessage() {
        String message = messageEditText.getText().toString();
        if (message.length() < 2 ) {
            messageEditText.setError("Please enter a longer message");
            return;
        }

        messageEditText.setError(null);

        if (request.getRecipient() == null) {
            Toast.makeText(this, "Please select a recipient", Toast.LENGTH_SHORT).show();
            selectRecipient();
            return;
        }

        progressFrame.setVisibility(View.VISIBLE);
        progressFrame.setAlpha(0);
        progressFrame.animate().alpha(1).setDuration(250).start();

        request.setMessage(message);
        bus.post(request);
    }

    @Subscribe
    public void onMessageSent (Messages.SendMessageResponse response){
        if (!response.didSucceed()) {
            response.showErrorToast(this);
            messageEditText.setError(response.getPropertyErrors("message"));
            progressFrame.animate().alpha(0).setDuration(360).withEndAction(new Runnable() {
                @Override
                public void run() {
                    progressFrame.setVisibility(View.GONE);
                }
            })
                    .start();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(RESULT_MESSAGE, response.Message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
