package com.example.maklumi.yora.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.Messages;
import com.example.maklumi.yora.services.entities.UserDetails;
import com.squareup.picasso.Picasso;

/**
 * Created by Maklumi on 24-02-16.
 */
public class SendMessageActivity extends BaseAuthenticatedActivity implements View.OnClickListener {
    public static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";

    public static final int MAX_IMAGE_HEIGHT = 1200;
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
            Picasso
                    .with(this)
                    .load(imageUri)
                    .into(imageView);

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
}
