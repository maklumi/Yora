package com.example.maklumi.yora.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.services.entities.ContactRequest;
import com.squareup.picasso.Picasso;

public class ContactRequestViewHolder extends RecyclerView.ViewHolder {
    private final TextView dispalyName;
    private final TextView createdAt;
    private final ImageView avatar;

    public ContactRequestViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_contact_request, parent, false));

        dispalyName = (TextView) itemView.findViewById(R.id.list_item_contact_request_displayName);
        createdAt = (TextView) itemView.findViewById(R.id.list_item_contact_request_createdAt);
        avatar = (ImageView) itemView.findViewById(R.id.list_item_contact_request_avatar);
    }

    public void populate(Context context, ContactRequest request) {
        dispalyName.setText(request.getUser().getDisplayName());
        Picasso.with(context).load(request.getUser().getAvatarUrl()).into(avatar);

        String dateText = DateUtils.formatDateTime(
                context,
                request.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        if (request.isFromUs()) {
            createdAt.setText("Sent at " + dateText);
        } else {
            createdAt.setText("Received at " + dateText);
        }
    }
}
