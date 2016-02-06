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
import android.widget.Toast;

import ufms.br.com.ufmsapp.R;

public class RegistrarActivity extends AppCompatActivity {

    private EditText registrarNome;
    private EditText registrarEmail;
    private EditText registrarRga;
    private EditText registrarSenha;
    private ImageButton showPasswordRegistrar;
    private ImageButton hidePasswordRegistrar;

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

    public void goToLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void userSignIn(View view) {
        if (validateForm()) {
            Toast.makeText(this, "REGISTERED!!!", Toast.LENGTH_LONG).show();
        }
    }

    public void loadPicture(View view) {
        Toast.makeText(this, "LOAD PICTURE!!!", Toast.LENGTH_LONG).show();
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
            registrarNome.setError("Por favor, digite seu nome");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(registrarEmail.getText().toString())) {
            registrarEmail.setError("Por favor, digite um email");
            isFieldSet = false;
        } else if (!registrarEmail.getText().toString().contains("@")) {
            registrarEmail.setError("Por favor, digite uma email válido. Geralmente inclui um caractere '@'");
            isFieldSet = false;
        } else if (registrarRga.getText().length() != 12) {
            registrarRga.setError("Por favor, digite um RGA válido com 12 dígitos");
            isFieldSet = false;
        } else if (TextUtils.isEmpty(registrarSenha.getText().toString())) {
            registrarSenha.setError("Por favor, digite uma senha");
            isFieldSet = false;

        } else if (TextUtils.getTrimmedLength(registrarSenha.getText()) < 6) {
            registrarSenha.setError("Sua senha deve conter no mínimo 6 caracteres");
            isFieldSet = false;
        } else {
            isFieldSet = true;
        }

        return isFieldSet;

    }
}
