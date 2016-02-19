package ufms.br.com.ufmsapp.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_send_feedback:
                break;
            case R.id.action_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment())
                .commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isSoundEnabled = prefs.getBoolean(getResources().getString(R.string.pref_notification_sound), true);

        Toast.makeText(this, "SOUND =>" + isSoundEnabled, Toast.LENGTH_LONG).show();
    }


}
