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

package ufms.br.com.ufmsapp.gcm;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.google.android.gms.gcm.GcmListenerService;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.DetalhesDisciplinaActivity;
import ufms.br.com.ufmsapp.activity.DetalhesEventoActivity;
import ufms.br.com.ufmsapp.activity.NotasAtividadesActivity;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinasOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadEventosOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadNotas;
import ufms.br.com.ufmsapp.utils.Constants;

public class UfmsGcmListenerService extends GcmListenerService {

    public static final int NOTIFICATION_ID = 1;
    private static final String TYPE_EVENTO = "evento";
    private static final String TYPE_DISCIPLINA = "disciplina";
    private static final String TYPE_NOTA = "nota";


    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        UserSessionPreference userSession = new UserSessionPreference(getBaseContext());

        if (!userSession.isFirstTime()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean isNotificationEnabled = prefs.getBoolean(getResources().getString(R.string.pref_notification_enable), true);

            if (data != null) {
                String message = data.getString("mensagem");
                String type = data.getString("type");

                String eventoId = null;
                String disciplinaId = null;
                String notaId = null;

                if (data.getString("eventoId") != null) {
                    eventoId = data.getString("eventoId");
                }

                if (data.getString("disciplinaId") != null) {
                    disciplinaId = data.getString("disciplinaId");
                }

                if (data.getString("notaId") != null) {
                    notaId = data.getString("notaId");
                }

                if (!TextUtils.isEmpty(type) && type.equals(TYPE_EVENTO)) {
                    if (EventosFragment.isInForeground()) {
                        //sincronizar eventos com o servidor
                        new TaskLoadEventosOnStart().execute();
                        updateEventosList(MyApplication.getAppContext());

                    } else {
                        //sincronizar eventos com o servidor
                        new TaskLoadEventosOnStart().execute();

                        //disparar notificação
                        if (eventoId != null) {
                            if (isNotificationEnabled)
                                buildGcmNotification(getString(R.string.txt_notificacao_eventos), message, DetalhesEventoActivity.class, Constants.EVENTO_CREATED_EXTRA, eventoId);
                        }
                    }

                } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_DISCIPLINA)) {
                    if (DisciplinasFragment.isInForeground()) {
                        new TaskLoadDisciplinasOnStart().execute();
                        updateDisciplinasList(MyApplication.getAppContext());
                    } else {
                        //sincronizar disciplinas com o servidor
                        new TaskLoadDisciplinasOnStart().execute();

                        //disparar notificação
                        if (disciplinaId != null) {
                            if (isNotificationEnabled)
                                buildGcmNotification(getString(R.string.txt_notificacao_disciplinas), message, DetalhesDisciplinaActivity.class, Constants.DISCIPLINA_CREATED_EXTRA, disciplinaId);
                        }

                    }

                } else if (!TextUtils.isEmpty(type) && type.equals(TYPE_NOTA)) {
                    if (NotasFragment.isInForeground()) {
                        new TaskLoadNotas().execute();
                        updateNotasLista(MyApplication.getAppContext());
                    } else {
                        //sincroniza notas com o servidor
                        new TaskLoadNotas().execute();

                        //disparar notificação
                        if (notaId != null) {
                            if (isNotificationEnabled)
                                buildGcmNotification(getString(R.string.txt_notificacao_notas), message, NotasAtividadesActivity.class, Constants.NOTA_CREATED_EXTRA, notaId);
                        }
                    }

                }
            }
        }


    }

    static void updateEventosList(Context context) {
        Intent intent = new Intent(Constants.UPDATE_EVENTO_INTENT);
        context.sendBroadcast(intent);
    }

    static void updateDisciplinasList(Context context) {
        Intent intent = new Intent(Constants.UPDATE_DISCIPLINA_INTENT);
        context.sendBroadcast(intent);
    }

    static void updateNotasLista(Context context) {
        Intent intent = new Intent(Constants.UPDATE_NOTA_INTENT);
        context.sendBroadcast(intent);
    }


    private void buildGcmNotification(String title, String msg, Class mClass, String intentExtraName, String valueId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isSoundEnabled = prefs.getBoolean(getResources().getString(R.string.pref_notification_sound), true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        Intent it = new Intent(this, mClass);
        it.putExtra(intentExtraName, valueId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(it);
        PendingIntent pit = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        if (isSoundEnabled) {
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
        }
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pit);
        mBuilder.setColor(ContextCompat.getColor(this, R.color.accentColor));
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(msg);
        nm.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
