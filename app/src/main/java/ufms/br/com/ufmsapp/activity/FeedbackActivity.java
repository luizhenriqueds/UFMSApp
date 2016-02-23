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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ufms.br.com.ufmsapp.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userMessageFeedback;

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
                    Snackbar.make(findViewById(android.R.id.content), R.string.txt_feedback_message_success, Snackbar.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_clear_white_24dp);

        }

        userEmail = (EditText) findViewById(R.id.feedback_user_email);
        userMessageFeedback = (EditText) findViewById(R.id.feedback_user_message);

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
