package com.example.maklumi.yora.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.activities.BaseActivity;
import com.example.maklumi.yora.activities.MainActivity;
import com.example.maklumi.yora.infrastructure.User;

/**
 * Created by Maklumi on 17-02-16.
 */
public class MainNavDrawer extends NavDrawer {

    private final TextView displayNameText;
    private final ImageView avatarImage;

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class, "Inbox", null, R.drawable.ic_badge, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(MainActivity.class, "Sent messages", null, R.drawable.ic_email, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(MainActivity.class, "Contact", null, R.drawable.ic_setting, R.id.include_man_nav_drawer_topItem));
        addItem(new ActivityNavDrawerItem(MainActivity.class, "Profile", null, R.drawable.ic_face, R.id.include_man_nav_drawer_topItem));

        addItem(new BasicNavDrawerItem("Logout", null, R.drawable.ic_exit, R.id.include_man_nav_drawer_bottomItem) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                Toast.makeText(activity, "You log out", Toast.LENGTH_LONG).show();
            }
        });

        displayNameText = (TextView) navDrawerView.findViewById(R.id.include_man_nav_drawer_displayName);
        avatarImage = (ImageView) navDrawerView.findViewById(R.id.include_man_nav_drawer_avatar);

        User loggedInUser = activity.getYoraApplication().getAuth().getUser();
        displayNameText.setText(loggedInUser.getDisplayName());

        //todo change avatar

    }
}
