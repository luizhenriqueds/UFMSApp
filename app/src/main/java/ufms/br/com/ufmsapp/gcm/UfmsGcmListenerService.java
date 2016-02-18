package ufms.br.com.ufmsapp.gcm;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.DetalhesEventoActivity;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
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
            String eventoId = data.getString("eventoId");
            Log.d("GCM_TEST", "Message: " + message);
            Log.d("GCM_TEST", "TYPE: " + type);
            Log.d("GCM_TEST", "EVENTO ID: " + eventoId);

            if (!TextUtils.isEmpty(type) && type.equals(TYPE_EVENTO)) {
                if (EventosFragment.isInForeground()) {
                    //sincronizar eventos com o servidor
                    new TaskLoadEventosOnStart().execute();
                    updateEventosList(MyApplication.getAppContext());

                } else {
                    //sincronizar eventos com o servidor
                    new TaskLoadEventosOnStart().execute();

                    //disparar notificação
                    buildGcmNotification(getString(R.string.txt_notificacao_eventos), message, eventoId);
                }

            } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_DISCIPLINA)) {
                if (DisciplinasFragment.isInForeground()) {
                    new TaskLoadDisciplinasOnStart().execute();
                    updateDisciplinasList(MyApplication.getAppContext());
                } else {
                    //sincronizar disciplinas com o servidor
                    new TaskLoadDisciplinasOnStart().execute();

                    //disparar notificação
                    // buildGcmNotification(getString(R.string.txt_notificacao_disciplinas), message);
                }

            } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_NOTA)) {
                if (NotasFragment.isInForeground()) {
                    new TaskLoadNotas().execute();
                    updateNotasLista(MyApplication.getAppContext());
                } else {
                    //sincroniza notas com o servidor
                    new TaskLoadNotas().execute();

                    //disparar notificação
                    // buildGcmNotification(getString(R.string.txt_notificacao_notas), message);
                }

            }
        }

    }

    static void updateEventosList(Context context) {
        Intent intent = new Intent("updateDisciplinas");
        context.sendBroadcast(intent);
    }

    static void updateDisciplinasList(Context context) {
        Intent intent = new Intent("updateEventos");
        context.sendBroadcast(intent);
    }

    static void updateNotasLista(Context context) {
        Intent intent = new Intent("updateNotas");
        context.sendBroadcast(intent);
    }


    private void buildGcmNotification(String title, String msg, String eventoId) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        Intent it = new Intent(this, DetalhesEventoActivity.class);
        it.putExtra("EVENTO_CREATED", eventoId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(it);
        //PendingIntent viewPendingIntent =
        //PendingIntent.getActivity(this, 0, it, 0);
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
