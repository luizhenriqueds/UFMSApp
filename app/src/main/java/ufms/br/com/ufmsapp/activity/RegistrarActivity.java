package ufms.br.com.ufmsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ufms.br.com.ufmsapp.R;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

    }

    public void goToLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void userSignIn(View view) {
    }

    public void loadPicture(View view) {
        Toast.makeText(this, "LOAD PICTURE!!!", Toast.LENGTH_LONG).show();
    }
}
