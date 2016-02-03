package ufms.br.com.ufmsapp.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.ExploreFragment;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.gcm.UfmsListenerService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private static final int JOB_ID = 100;

    protected Toolbar toolbar;
    //JobScheduler mJobScheduler;
    private int mSelectedPosition;
    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    private static final String SELECTED_MENU_ITEM = "menuItem";

    public static final int REQUEST_PLAY_SERVICES = 1;

    private static NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setHomeContent();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            mSelectedPosition = R.id.nav_drawer_explore;
        } else {
            mSelectedPosition = savedInstanceState.getInt(SELECTED_MENU_ITEM);
        }

        selectOptionsMenu(navigationView.getMenu().findItem(mSelectedPosition));

        startGooglePlayService();
    }


    public void setHomeContent() {
        FragmentManager manager = getSupportFragmentManager();

        if (manager != null) {
            manager.beginTransaction().replace(R.id.main_layout_container, ExploreFragment.newInstance()).commit();

        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();    //remove all items
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectOptionsMenu(item);
        return true;
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


    private void selectOptionsMenu(MenuItem menuItem) {
        mSelectedPosition = menuItem.getItemId();

        Fragment fragment;

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

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

        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
