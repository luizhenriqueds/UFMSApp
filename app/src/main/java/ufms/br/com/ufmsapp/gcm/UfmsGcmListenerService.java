package ufms.br.com.ufmsapp.gcm;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.MainActivity;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinasOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadEventosOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadNotas;

public class UfmsGcmListenerService extends GcmListenerService {

    public static final int NOTIFICATION_ID = 1;
    private static final String TYPE_EVENTO = "evento";
    private static final String TYPE_DISCIPLINA = "disciplina";
    private static final String TYPE_NOTA = "nota";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        if (data != null) {
            String message = data.getString("mensagem");
            String type = data.getString("type");
            Log.d("GCM_TEST", "Message: " + message);
            Log.d("GCM_TEST", "TYPE: " + type);

            if (!TextUtils.isEmpty(type) && type.equals(TYPE_EVENTO)) {
                //sincronizar eventos com o servidor
                new TaskLoadEventosOnStart().execute();

                //disparar notificação
                buildGcmNotification(getString(R.string.txt_notificacao_eventos), message);
            } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_DISCIPLINA)) {
                //sincronizar disciplinas com o servidor
                new TaskLoadDisciplinasOnStart().execute();

                //disparar notificação
                buildGcmNotification(getString(R.string.txt_notificacao_disciplinas), message);
            } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_NOTA)) {
                //sincroniza notas com o servidor
                new TaskLoadNotas().execute();

                //disparar notificação
                buildGcmNotification(getString(R.string.txt_notificacao_notas), message);
            }
        }

    }

    private void buildGcmNotification(String title, String msg) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        Intent it = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(it);
        PendingIntent pit = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pit)
                        .setColor(ContextCompat.getColor(this, R.color.accentColor))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg);
        nm.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
