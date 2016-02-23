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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.utils.PasswordEncryptionUtil;

public class RegistrarActivity extends AppCompatActivity {

    private static final String REGISTER_ACCESS_URL = "http://www.henriqueweb.com.br/webservice/access/register.php?acao=register";

    public static final String URL_DO_SERVIDOR = UrlEndpoints.URL_ENDPOINT + "server/updateUserGCM.php";

    public static final String UPDATED_SERVIDOR = "updatedServidor";

    private static final String PARAM_NOME = "nome";

    private static final String PARAM_EMAIL = "email";

    private static final String PARAM_RGA = "rga";

    private static final String PARAM_STATUS_ALUNO = "statusAlunoFk";

    private static final String PARAM_PASSWORD = "password";

    private EditText registrarNome;
    private EditText registrarEmail;
    private EditText registrarRga;
    private EditText registrarSenha;
    private ImageButton showPasswordRegistrar;
    private ImageButton hidePasswordRegistrar;
    private Button btnSignIn;
    UserSessionPreference prefs;
    private CircularProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        registrarNome = (EditText) findViewById(R.id.signin_nome);
        registrarEmail = (EditText) findViewById(R.id.signin_email);
        registrarRga = (EditText) findViewById(R.id.signin_rga);
        registrarSenha = (EditText) findViewById(R.id.signin_password);
        showPasswordRegistrar = (ImageButton) findViewById(R.id.sign_up_show_button);
        hidePasswordRegistrar = (ImageButton) findViewById(R.id.sign_up_hide_button);
        btnSignIn = (Button) findViewById(R.id.signin_button);
        prefs = new UserSessionPreference(this);
        progressBar = (CircularProgressBar) findViewById(R.id.progress_bar_registrar);

        registrarSenha.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showPasswordRegistrar.setVisibility(View.VISIBLE);
                } else if (s.length() == 0) {
                    showPasswordRegistrar.setVisibility(View.GONE);
                    hidePasswordRegistrar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void enableProgress(boolean indeterminate, int visible) {
        progressBar.setVisibility(visible);
        progressBar.setIndeterminate(indeterminate);
    }

    public void goToLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void userSignIn(View view) {
        if (validateForm()) {

            enableProgress(true, View.VISIBLE);

            final StringRequest postRequest = new StringRequest(Request.Method.POST, REGISTER_ACCESS_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonResponse;

                            try {
                                jsonResponse = new JSONObject(response);

                                int inserted = jsonResponse.getInt("registered");
                                int userId = jsonResponse.getInt("userId");

                                if (inserted == -1) {
                                    btnSignIn.setEnabled(true);
                                    enableProgress(false, View.GONE);
                                    Snackbar.make(findViewById(android.R.id.content), R.string.txt_invalid_registrar, Snackbar.LENGTH_LONG).show();
                                } else if (inserted > 0) {
                                    enableProgress(false, View.GONE);

                                    btnSignIn.setEnabled(false);

                                    Aluno aluno = new Aluno(registrarNome.getText().toString(), registrarEmail.getText().toString(), String.valueOf(registrarRga.getText().toString()), 1, jsonResponse.getInt("userId"));

                                    int returnedId = MyApplication.getWritableDatabase().criarAluno(aluno);

                                    if (returnedId != -1) {

                                        if (!getUpdatedServidor()) {
                                            registerUser(userId);
                                        }

                                        prefs.setName(aluno.getNome());
                                        prefs.setEmail(aluno.getEmail());
                                        prefs.setOld(true);

                                        startActivity(new Intent(RegistrarActivity.this, MainActivity.class));
                                        RegistrarActivity.this.finish();
                                    }

                                } else {
                                    btnSignIn.setEnabled(true);
                                    enableProgress(false, View.GONE);
                                    Snackbar.make(findViewById(android.R.id.content), R.string.txt_registrar_error, Snackbar.LENGTH_LONG).show();
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
                    String password = PasswordEncryptionUtil.md5(registrarSenha.getText().toString().concat(salt));

                    Map<String, String> params = new HashMap<>();
                    params.put(PARAM_NOME, registrarNome.getText().toString());
                    params.put(PARAM_EMAIL, registrarEmail.getText().toString());
                    params.put(PARAM_RGA, String.valueOf(registrarRga.getText().toString()));
                    params.put(PARAM_STATUS_ALUNO, String.valueOf(1));
                    params.put(PARAM_PASSWORD, password);

                    return params;
                }
            };

            VolleySingleton.getInstance().getRequestQueue().add(postRequest);

        }
    }

    public void loadPicture(View view) {
        Toast.makeText(this, "LOAD PICTURE!!!", Toast.LENGTH_LONG).show();
    }

    private void registerUser(final int alunoId) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InstanceID instanceID = InstanceID.getInstance(RegistrarActivity.this);
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

    public void showHidePasswordRegistrar(View view) {
        switch (view.getId()) {
            case R.id.sign_up_show_button:
                showPasswordRegistrar.setVisibility(View.GONE);
                hidePasswordRegistrar.setVisibility(View.VISIBLE);
                registrarSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.id.sign_up_hide_button:
                hidePasswordRegistrar.setVisibility(View.GONE);
                showPasswordRegistrar.setVisibility(View.VISIBLE);
                registrarSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
    }

    private boolean validateForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(registrarNome.getText().toString())) {
            registrarNome.setError(getString(R.string.txt_type_name));
            isFieldSet = false;
        } else if (TextUtils.isEmpty(registrarEmail.getText().toString())) {
            registrarEmail.setError(getString(R.string.txt_registrar_type_email));
            isFieldSet = false;
        } else if (!registrarEmail.getText().toString().contains("@")) {
            registrarEmail.setError(getString(R.string.txt_registrar_type_valid_email));
            isFieldSet = false;
        } else if (registrarRga.getText().length() != 12) {
            registrarRga.setError(getString(R.string.txt_registrar_valid_rga));
            isFieldSet = false;
        } else if (TextUtils.isEmpty(registrarSenha.getText().toString())) {
            registrarSenha.setError(getString(R.string.txt_registrar_type_password));
            isFieldSet = false;

        } else if (TextUtils.getTrimmedLength(registrarSenha.getText()) < 6) {
            registrarSenha.setError(getString(R.string.txt_registrar_min_chars_password));
            isFieldSet = false;
        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }
}
