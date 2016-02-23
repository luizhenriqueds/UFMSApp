/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
