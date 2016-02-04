package ufms.br.com.ufmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ufms.br.com.ufmsapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void userCreateAccount(View view) {
        startActivity(new Intent(this, RegistrarActivity.class));
    }

    public void userLogin(View view) {
        Toast.makeText(this, "LOGAR USUÁRIO!!", Toast.LENGTH_LONG).show();
    }

    public void showHidePassword(View view) {
        Toast.makeText(this, "SHOW/HIDE PASSCODE!!", Toast.LENGTH_LONG).show();
    }
}
