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


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;

public class UfmsListenerService extends InstanceIDListenerService {
    public static final String URL_SERVIDOR_GCM_REGISTRAR = UrlEndpoints.URL_ENDPOINT + "server/gcmServerRegister.php";
    //public static final String URL_SERVIDOR_GCM_EVENTOS = UrlEndpoints.URL_ENDPOINT + "server/gcmServerEventosHandler.php";
    //public static final String URL_SERVIDOR_GCM_DISCIPLINAS = UrlEndpoints.URL_ENDPOINT + "server/gcmServerDisciplinasHandler.php";
    //public static final String URL_SERVIDOR_GCM_NOTAS = UrlEndpoints.URL_ENDPOINT + "server/gcmServerNotasHandler.php";

    public static final String REGISTRATION_ID = "registrationId";
    public static final String ENVIADO_SERVIDOR = "getEnviadoServidor";
    public static final String EXTRA_REGISTRAR = "registrar";
    public static final int ALUNO_ID_REGISTRAR = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(EXTRA_REGISTRAR, false)) {
            try {
                if (getRegistrationId() == null) {
                    getToken();
                } else if (!getEnviadoServidor()) {
                    sendRegistrationIdToServer(getRegistrationId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        setRegistrationId(null);
        setEnviadoServidor(false);
        try {
            getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getToken() throws IOException {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InstanceID instanceID = InstanceID.getInstance(UfmsListenerService.this);
                try {
                    String token = instanceID.getToken(
                            getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    setRegistrationId(token);
                    sendRegistrationIdToServer(token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void sendRegistrationIdToServer(final String key) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_SERVIDOR_GCM_REGISTRAR);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    //Log.d("GCM_SERVICE", "I'M CALLING HERE");
                    os.write(("acao=registrar&regId=" + key + "&alunoId=" + ALUNO_ID_REGISTRAR).getBytes());
                    os.flush();
                    os.close();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        setEnviadoServidor(true);
                    } else {
                        throw new RuntimeException("Erro ao salvar no servidor");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setEnviadoServidor(boolean enviado) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ENVIADO_SERVIDOR, enviado);
        editor.apply();
    }

    private boolean getEnviadoServidor() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean(ENVIADO_SERVIDOR, false);
    }

    private void setRegistrationId(String regId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REGISTRATION_ID, regId);
        editor.apply();
    }

    public String getRegistrationId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString(REGISTRATION_ID, null);
    }
}
