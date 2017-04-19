package com.zakati.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ta.utils.Lg;

/**
 * Created by rahil on 27/9/16.
 */

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppFirebaseMessagingService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String payload = remoteMessage.getData().get("notification");
        Lg.e(TAG, payload);

    }


   /* private void showNotification(Notification data) {


        Intent intent = AppUtils.getIntentForPush(this, data);

        if (intent == null) return;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker(data.getAlert())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(data.getAlert())
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(android.app.Notification.PRIORITY_HIGH);
        }
        android.app.Notification notification = builder.build();


        //startForeground(Integer.parseInt(mPushObj.getData().getMsgId()), notification);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify((int) System.currentTimeMillis(), notification);


    }*/
}
