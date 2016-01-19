package ufms.br.com.ufmsapp.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.File;


public class NotificationBuilder {

    public static final int NOTIFICATION_ID = 100;
    static NotificationCompat.Builder builder;
    static NotificationManager manager;
    static PendingIntent piOpenNotification;
    static PendingIntent piOpenMap;
    static PendingIntent piDismiss;
    public static int increment;
    public static boolean isComplete;
    public static Context context;

    public NotificationBuilder(Context context) {
        NotificationBuilder.context = context;
        builder = new NotificationCompat.Builder(context);
    }


    public static void setIncrement(int increment) {
        NotificationBuilder.increment = increment;
    }

    public void cancelNotificationProgress() {
        builder.setProgress(0, 0, false);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void buildNotification(int iconResId,
                                         String title, String msg) {

        builder.setSmallIcon(iconResId);
        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setDefaults(Notification.DEFAULT_ALL);

        builder.setAutoCancel(true);

        builder.setProgress(100, increment, false);

        Intent intent = new Intent();


        piOpenNotification = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(piOpenNotification);

        Intent intentMap = new Intent();
        intent.setAction("com.android.training.mynotificationapp.openmap");
        piOpenMap = PendingIntent.getActivity(context, 0, intentMap,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDismiss = new Intent();
        intent.setAction("com.android.training.mynotificationapp.dismiss");
        piDismiss = PendingIntent.getActivity(context, 0, intentDismiss,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

        //builder.addAction(R.drawable.ic_place_grey600_24dp, "Map", piOpenMap);

        //builder.addAction(R.drawable.ic_stat_dismiss, "Dismiss", piDismiss);

        manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(NOTIFICATION_ID, builder.build());

    }
}
