package ufms.br.com.ufmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import ufms.br.com.ufmsapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText passwordLogin;
    private ImageButton showPasswordImgButton;
    private ImageButton hidePasswordImgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = (EditText) findViewById(R.id.login_email);
        passwordLogin = (EditText) findViewById(R.id.login_password);
        showPasswordImgButton = (ImageButton) findViewById(R.id.login_show_button);
        hidePasswordImgButton = (ImageButton) findViewById(R.id.login_hide_button);

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

    public void userCreateAccount(View view) {
        startActivity(new Intent(this, RegistrarActivity.class));
    }

    public void userLogin(View view) {
        if (validateForm()) {
            startActivity(new Intent(this, MainActivity.class));
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

    private boolean validateForm() {

        boolean isFieldSet;

        if (TextUtils.isEmpty(emailLogin.getText().toString())) {
            emailLogin.setError("Por favor, digite um email");
            isFieldSet = false;
        } else if (!emailLogin.getText().toString().contains("@")) {
            emailLogin.setError("Por favor, digite uma email válido. Geralmente inclui um caractere '@'");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(passwordLogin.getText().toString())) {
            passwordLogin.setError("Por favor, digite uma senha");
            isFieldSet = false;

        } else if (TextUtils.getTrimmedLength(passwordLogin.getText()) < 6) {
            passwordLogin.setError("Sua senha deve conter no mínimo 6 caracteres");
            isFieldSet = false;
        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }
}
