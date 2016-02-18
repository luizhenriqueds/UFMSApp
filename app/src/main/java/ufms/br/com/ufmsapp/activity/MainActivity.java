package ufms.br.com.ufmsapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.data.DataHelper;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.ExploreFragment;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.utils.PasswordEncryptionUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    public static final String URL_DO_SERVIDOR = UrlEndpoints.URL_ENDPOINT + "server/updateUserGCM.php";

    public static final String UPDATED_SERVIDOR = "updatedServidor";

    protected Toolbar toolbar;
    private int mSelectedPosition;
    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    protected TextView nameUserLogged;
    protected TextView emailUserLogged;

    private static final String SELECTED_MENU_ITEM = "menuItem";


    private static NavigationView navigationView = null;
    protected UserSessionPreference prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHelper.newInstance(this).getWritableDatabase();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String seedValue = "201105700070";
        String password = "LuizLuiz10**";
        String normalTextEnc;
        String normalTextDec;

        try {
            normalTextEnc = PasswordEncryptionUtil.encrypt(password, seedValue);
            normalTextDec = PasswordEncryptionUtil.decrypt(normalTextEnc, seedValue);
            Log.i("ENCRYPT", normalTextEnc);
            Log.i("ENCRYPT", normalTextDec);
        } catch (Exception e) {
            e.printStackTrace();
        }


        prefs = new UserSessionPreference(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        nameUserLogged = (TextView) header.findViewById(R.id.name_user_logged);
        emailUserLogged = (TextView) header.findViewById(R.id.email_user_logged);


        if (!prefs.isFirstTime()) {
            nameUserLogged.setText(prefs.getName());
            emailUserLogged.setText(prefs.getEmail());
        }

        if (savedInstanceState == null) {
            mSelectedPosition = R.id.nav_drawer_explore;
        } else {
            mSelectedPosition = savedInstanceState.getInt(SELECTED_MENU_ITEM);
        }


        selectOptionsMenu(navigationView.getMenu().findItem(mSelectedPosition));

    }


    private void registerUser(final int alunoId) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Atualizado com sucesso", Toast.LENGTH_LONG).show();
                            }
                        });
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

    public static void setNavSelected(int resId) {
        navigationView.setCheckedItem(resId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU_ITEM, mSelectedPosition);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectOptionsMenu(item);
        return true;
    }


    private void selectOptionsMenu(MenuItem menuItem) {
        mSelectedPosition = menuItem.getItemId();

        Fragment fragment;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (mSelectedPosition) {
            case R.id.nav_drawer_explore:
                //fragment = FragmentEventCalendarView.newInstance();
                fragment = ExploreFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_drawer_eventos:
                fragment = EventosFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_drawer_disciplina:
                fragment = DisciplinasFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_drawer_notas:
                fragment = NotasFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_drawer_curso:
                prefs.logOut();
                Toast.makeText(this, "LOGOUT!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("BUSCA", query);
        Toast.makeText(this, "BUSCA ==>" + query, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
