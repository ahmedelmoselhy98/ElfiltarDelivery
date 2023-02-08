package com.elfiltar.elfiltartechnician.commons.fcm;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.elfiltar.elfiltartechnician.R;
import com.elfiltar.elfiltartechnician.data.local.session.SessionHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationChannel mChannel;
    private NotificationManager notifManager;
    public String channelId = "0";
    public String channelName = "HDD";
    @Inject
    public SessionHelper sessionHelper;

    Intent intent;
    String img = null, icon = null;

    @Override
    public void onNewToken(@NonNull String token) {
        // Get new FCM registration token
        Log.e("MyFirebaseMessaging", "Current token=$token");
        sessionHelper.savePushNotificationToken(token);
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            String title = "0";
            String body = "0";
            String id = "0";
            String notifyId = "0";
            String entityId = "0";
            String subEntityId = "0";
            String unSeenChatsCount = "";
            String unSeenNotificationCount = "";
            String type = "";
            String clickAction = "";
            id = remoteMessage.getData().get("id");
            notifyId = remoteMessage.getData().get("notifyId");
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
            img = remoteMessage.getData().get("largeImage");
            icon = remoteMessage.getData().get("icon");
            type = remoteMessage.getData().get("type");
            unSeenChatsCount = remoteMessage.getData().get("unSeenChatsCount");
            unSeenNotificationCount = remoteMessage.getData().get("unSeenNotificationCount");
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + body);
            Log.e(TAG, "icon: " + icon);
            Log.e(TAG, "largeImage: " + img);
            Log.e(TAG, "entityId: " + entityId);
            intent = new Intent();
            channelId = type;
            notify(title, entityId, body, img + "", clickAction);
        }
    }

    public void notify(String title, String id, String message, String img, String click_action) {
        Random random = new Random();
        int randoNum = random.nextInt(9999 - 1000) + 1000;
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService
                    (Context.NOTIFICATION_SERVICE);
        }
        int pendingFlags = PendingIntent.FLAG_ONE_SHOT;
        pendingFlags = PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        (channelId, channelName, importance);
                mChannel.setDescription("");
                mChannel.enableVibration(true);
                mChannel.setShowBadge(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, channelId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, pendingFlags);
            builder.setContentTitle(title)
                    .setSmallIcon(getNotificationIcon()) // required
                    .setContentText(message)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setGroup(channelId)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setChannelId(mChannel.getId())
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri
                            (RingtoneManager.TYPE_NOTIFICATION));
//            if (!TextUtils.isEmpty(icon)) {
//                if (getBitmapFromURL(icon) != null) {
//                    builder.setLargeIcon(getBitmapFromURL(icon));
//                }
//            }
//            if (!TextUtils.isEmpty(img)) {
//                if (getBitmapFromURL(img) != null) {
//                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
//                    builder.setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(getBitmapFromURL(img))
//                            .bigLargeIcon(null));
//                }
//            }
            Notification notification = builder.build();
            notifManager.notify(randoNum, notification);
        } else {
            Intent intent = new Intent(click_action);
            intent.putExtra("orderId", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = null;
            pendingIntent = PendingIntent.getActivity(this, 1251, intent, pendingFlags);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getBaseContext(), R.color.primary))
                    .setSound(defaultSoundUri)
                    .setGroup(channelId)
                    .setGroupSummary(true)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setSmallIcon(getNotificationIcon())
                    .setContentIntent(pendingIntent);
//            if (!TextUtils.isEmpty(icon)) {
//                if (getBitmapFromURL(icon) != null) {
//                    notificationBuilder.setLargeIcon(getBitmapFromURL(icon));
//                }
//            }
//            if (!TextUtils.isEmpty(img)) {
//                if (getBitmapFromURL(img) != null) {
//                    notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
//                    notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(""));
//                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(getBitmapFromURL(img))
//                            .bigLargeIcon(null));
//                }
//            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(randoNum, notificationBuilder.build());
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public Bitmap getBitmapFromURL(String strURL) {
        if (strURL == null || strURL.isEmpty())
            return null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
