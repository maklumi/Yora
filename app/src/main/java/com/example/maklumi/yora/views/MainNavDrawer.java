package com.example.maklumi.yora.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.activities.BaseActivity;
import com.example.maklumi.yora.activities.ContactsActivity;
import com.example.maklumi.yora.activities.MainActivity;
import com.example.maklumi.yora.activities.ProfileActivity;
import com.example.maklumi.yora.activities.SentMessagesActivity;
import com.example.maklumi.yora.infrastructure.User;
import com.example.maklumi.yora.services.Account;
import com.squareup.otto.Subscribe;

/**
 * Created by Maklumi on 17-02-16.
 */
public class MainNavDrawer extends NavDrawer {

    private final TextView displayNameText;
    private final ImageView avatarImage;

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class, "Inbox", null, R.drawable.ic_email, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class, "Sent messages", null, R.drawable.ic_sent_plane, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class, "Contact", null, R.drawable.ic_contacts, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class, "Profile", null, R.drawable.ic_contact_outline, R.id.include_man_nav_drawer_topItem));

        addItem(new BasicNavDrawerItem("Logout", null, R.drawable.ic_exit, R.id.include_man_nav_drawer_bottomItem) {
            @Override
            public void onClick(View v) {
             activity.getYoraApplication().getAuth().logout();
            }
        });

        displayNameText = (TextView) navDrawerView.findViewById(R.id.include_man_nav_drawer_displayName);
        avatarImage = (ImageView) navDrawerView.findViewById(R.id.include_man_nav_drawer_avatar);

        User loggedInUser = activity.getYoraApplication().getAuth().getUser();
        displayNameText.setText(loggedInUser.getDisplayName());

        //todo change avatar

    }

    @Subscribe
    public void onUserDetailsUpdated(Account.UserDetailsUpdatedEvent event){
        // TODO: 20-02-16 update avatar url
        displayNameText.setText(event.user.getDisplayName());
    }
}
