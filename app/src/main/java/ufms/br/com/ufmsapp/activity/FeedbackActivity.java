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

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class FeedbackActivity extends AppCompatActivity {

    private static final String SEND_FEEDBACK_ENDPOINT_URL = "http://henriqueweb.com.br/webservice/insert/sendFeedback.php";

    private EditText userEmail;
    private EditText userMessageFeedback;
    private TextView sendingMessageProgress;
    private CircularProgressBar progressBar;
    private UserSessionPreference prefs;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feedback_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_feedback_send_button:
                if (validateForm()) {
                    if (!prefs.isFirstTime()) {
                        sendUserFeedback();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableProgress(boolean indeterminate, int visible) {
        progressBar.setVisibility(visible);
        progressBar.setIndeterminate(indeterminate);
        sendingMessageProgress.setVisibility(visible);
    }

    private void sendUserFeedback() {
        enableProgress(true, View.VISIBLE);
        final StringRequest postRequest = new StringRequest(Request.Method.POST, SEND_FEEDBACK_ENDPOINT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;

                        try {
                            jsonResponse = new JSONObject(response);

                            int inserted = jsonResponse.getInt("sent");

                            if (inserted > 0) {
                                enableProgress(false, View.GONE);
                            } else {
                                enableProgress(false, View.GONE);
                            }

                            finish();

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
                Map<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(MyApplication.getWritableDatabase().alunoByEmail(userEmail.getText().toString()).getAlunoIdServidor()));
                params.put("message", userMessageFeedback.getText().toString());


                return params;
            }
        };

        VolleySingleton.getInstance().getRequestQueue().add(postRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = new UserSessionPreference(FeedbackActivity.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_clear_white_24dp);

        }

        sendingMessageProgress = (TextView) findViewById(R.id.sending_feedback_text);
        progressBar = (CircularProgressBar) findViewById(R.id.progress_bar_send_feedback);

        userEmail = (EditText) findViewById(R.id.feedback_user_email);
        if (prefs != null) {
            userEmail.setText(prefs.getEmail());
        }

        userMessageFeedback = (EditText) findViewById(R.id.feedback_user_message);
        userMessageFeedback.requestFocus();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private boolean validateForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(userEmail.getText().toString())) {
            userEmail.setError(getString(R.string.txt_registrar_type_email));
            isFieldSet = false;
        } else if (!userEmail.getText().toString().contains("@")) {
            userEmail.setError(getString(R.string.txt_registrar_type_valid_email));
            isFieldSet = false;
        } else if (TextUtils.isEmpty(userMessageFeedback.getText().toString())) {
            userMessageFeedback.setError(getString(R.string.txt_feedback_message_empty));
            isFieldSet = false;

        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }

}
