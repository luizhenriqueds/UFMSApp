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

package ufms.br.com.ufmsapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.data.DataHelper;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.gcm.UfmsListenerService;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.task.TaskLoadAlunos;
import ufms.br.com.ufmsapp.utils.PasswordEncryptionUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_ACCESS_URL = "http://www.henriqueweb.com.br/webservice/access/userAccess.php?acao=login";
    public static final String URL_DO_SERVIDOR = UrlEndpoints.URL_ENDPOINT + "server/updateUserGCM.php";
    public static final String UPDATED_SERVIDOR = "updatedServidor";
    public static final String EMAIL_PARAM = "email";
    public static final String PASSWORD_PARAM = "password";
    private EditText emailLogin;
    private EditText passwordLogin;
    private ImageButton showPasswordImgButton;
    private ImageButton hidePasswordImgButton;
    private UserSessionPreference prefs;
    protected CircularProgressBar progressBar;

    public static final int REQUEST_PLAY_SERVICES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // startGooglePlayService();

        prefs = new UserSessionPreference(this);

        if (!prefs.isFirstTime()) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            this.finish();
        } else {
            new TaskLoadAlunos().execute();
        }


        emailLogin = (EditText) findViewById(R.id.login_email);
        passwordLogin = (EditText) findViewById(R.id.login_password);
        showPasswordImgButton = (ImageButton) findViewById(R.id.login_show_button);
        hidePasswordImgButton = (ImageButton) findViewById(R.id.login_hide_button);
        progressBar = (CircularProgressBar) findViewById(R.id.progress_bar_login);


        passwordLogin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showPasswordImgButton.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    showPasswordImgButton.setVisibility(View.GONE);
                    hidePasswordImgButton.setVisibility(View.GONE);
                }
            }
        });

    }

    private void startGooglePlayService() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                Dialog dialog = api.getErrorDialog(this, resultCode, REQUEST_PLAY_SERVICES);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                Toast.makeText(this, R.string.gcm_nao_suportado,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Intent it = new Intent(this, UfmsListenerService.class);
            it.putExtra(UfmsListenerService.EXTRA_REGISTRAR, true);
            startService(it);
        }
    }


    public void userCreateAccount(View view) {
        startActivity(new Intent(this, RegistrarActivity.class));
        finish();
    }

    private void enableProgress(boolean indeterminate, int visible) {
        progressBar.setVisibility(visible);
        progressBar.setIndeterminate(indeterminate);
    }

    public void userLogin(View view) {
        if (validateForm()) {

            enableProgress(true, View.VISIBLE);

            final StringRequest postRequest = new StringRequest(Request.Method.POST, LOGIN_ACCESS_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonResponse;

                            try {
                                jsonResponse = new JSONObject(response);

                                if (jsonResponse.getInt("result") == 1) {
                                    enableProgress(false, View.GONE);
                                    Aluno aluno = MyApplication.getWritableDatabase().alunoByEmail(emailLogin.getText().toString());

                                    prefs.setName(aluno.getNome());
                                    prefs.setEmail(aluno.getEmail());
                                    prefs.setOld(true);

                                    if (!getUpdatedServidor()) {
                                        registerUser(aluno.getAlunoIdServidor());
                                        DataHelper.sincronizarDados();
                                    }

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();


                                } else {
                                    enableProgress(false, View.GONE);
                                    Snackbar.make(findViewById(android.R.id.content), R.string.txt_login_invalido, Snackbar.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            enableProgress(false, View.GONE);
                            Log.d("Error.Response", error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {

                    String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
                    String password = PasswordEncryptionUtil.md5(passwordLogin.getText().toString().concat(salt));

                    Map<String, String> params = new HashMap<>();
                    params.put(EMAIL_PARAM, emailLogin.getText().toString());
                    params.put(PASSWORD_PARAM, password);

                    return params;
                }
            };

            VolleySingleton.getInstance().getRequestQueue().add(postRequest);
        }
    }


    public void showHidePassword(View view) {
        switch (view.getId()) {
            case R.id.login_show_button:
                showPasswordImgButton.setVisibility(View.GONE);
                hidePasswordImgButton.setVisibility(View.VISIBLE);
                passwordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.id.login_hide_button:
                hidePasswordImgButton.setVisibility(View.GONE);
                showPasswordImgButton.setVisibility(View.VISIBLE);
                passwordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
    }

    private void registerUser(final int alunoId) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InstanceID instanceID = InstanceID.getInstance(LoginActivity.this);
                try {
                    String token = instanceID.getToken(
                            getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    updateRegistrationOnServer(token, alunoId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void updateRegistrationOnServer(final String key, final int alunoId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_DO_SERVIDOR);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(("acao=updateUser&regId=" + key + "&alunoId=" + alunoId).getBytes());
                    os.flush();
                    os.close();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        setUpdatedServidor(true);
                    } else {
                        throw new RuntimeException("Erro ao atualizar servidor");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void setUpdatedServidor(boolean updated) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(UPDATED_SERVIDOR, updated);
        editor.apply();
    }

    private boolean getUpdatedServidor() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean(UPDATED_SERVIDOR, false);
    }

    private boolean validateForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(emailLogin.getText().toString())) {
            emailLogin.setError(getString(R.string.txt_registrar_type_email));
            isFieldSet = false;
        } else if (!emailLogin.getText().toString().contains("@")) {
            emailLogin.setError(getString(R.string.txt_registrar_type_valid_email));
            isFieldSet = false;
        } else if (TextUtils.isEmpty(passwordLogin.getText().toString())) {
            passwordLogin.setError(getString(R.string.txt_registrar_type_password));
            isFieldSet = false;

        } else if (TextUtils.getTrimmedLength(passwordLogin.getText()) < 6) {
            passwordLogin.setError(getString(R.string.txt_registrar_min_chars_password));
            isFieldSet = false;
        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }
}
