package com.example.maklumi.yora.receivers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.maklumi.yora.R;
import com.example.maklumi.yora.activities.MainActivity;
import com.example.maklumi.yora.activities.MessageActivity;
import com.example.maklumi.yora.infrastructure.Auth;
import com.example.maklumi.yora.infrastructure.YoraApplication;
import com.example.maklumi.yora.services.Events;
import com.example.maklumi.yora.services.entities.Message;
import com.squareup.okhttp.Interceptor;
import com.squareup.otto.Bus;

public class NotificationReceiver extends BroadcastReceiver {
	private static final String TAG = "NotifcationReceiver";

	private Auth auth;
	private YoraApplication application;

	@Override
	public void onReceive(Context context, Intent intent) {
		application = (YoraApplication) context.getApplicationContext();
		auth = application.getAuth();
		Bus bus = application.getBus();

		try {
			int operation = Integer.parseInt(intent.getStringExtra("operation"));
			int type = Integer.parseInt(intent.getStringExtra("type"));
			int entityId = Integer.parseInt(intent.getStringExtra("entityId"));
			int entityOwnerId = Integer.parseInt(intent.getStringExtra("entityOwnerId"));
			String entityOwnerName = intent.getStringExtra("entityOwnerName");

			Log.e(TAG, operation + ", " + type + ", " + entityId + ", " + entityOwnerId + ", " + entityOwnerName);

			Events.OnNotificationRecveivedEvent event = new Events.OnNotificationRecveivedEvent(
				operation, type, entityId, entityOwnerId, entityOwnerName);

			if (type == Events.ENTITY_CONTACT) {
				sendContactNotification(event);
			} else if (type == Events.ENTITY_CONTACT_REQUEST) {
				sendContactRequestNotification(event);
			} else if (type == Events.ENTITY_MESSAGE) {
				sendMessageNotification(event);
			}

			bus.post(event);
		} catch (NumberFormatException e) {
			Log.e(TAG, "Error parsing message", e);
		}

		setResultCode(Activity.RESULT_OK);
	}

	private void sendContactNotification(Events.OnNotificationRecveivedEvent event) {
	}

	private void sendContactRequestNotification(Events.OnNotificationRecveivedEvent event) {
		if (event.OperationType == Events.OPERATION_DELETED || event.EntityOwnerId == auth.getUser().getId()) {
			return;
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(application)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle(event.EntityOwnerName + " sent you a contact request on Yora!")
			.setContentText("Click here to view it");

		sendNotification(event.EntityId, builder, new Intent(application, MainActivity.class));
	}

	private void sendMessageNotification(Events.OnNotificationRecveivedEvent event) {
		if (event.OperationType == Events.OPERATION_DELETED || event.EntityOwnerId == auth.getUser().getId()) {
			return;
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(application)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle(event.EntityOwnerName + " sent you a message on Yora!")
			.setContentText("Click here to view it");

		Intent intent = new Intent(application, MessageActivity.class);
		intent.putExtra(MessageActivity.EXTRA_MESSAGE_ID, event.EntityId);
		sendNotification(event.EntityId, builder, intent);
	}

	private void sendNotification(int entityId, NotificationCompat.Builder builder, Intent intent) {
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(application);
		stackBuilder.addNextIntent(intent);

		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(pendingIntent);

		Notification notification = builder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(entityId, notification);
	}
}
