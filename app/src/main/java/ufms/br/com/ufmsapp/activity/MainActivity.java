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
import android.os.Bundle;
import android.os.PersistableBundle;
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

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.data.DataHelper;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.ExploreFragment;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

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

        prefs = new UserSessionPreference(this);

        if (prefs.isFirstTime()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DataHelper.newInstance(this).getWritableDatabase();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

       /* String seedValue = "201105700070";
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
        }*/


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


        //selectOptionsMenu(navigationView.getMenu().findItem(mSelectedPosition));

        findMenuItem(mSelectedPosition);

    }

    public void findMenuItem(int item) {
        selectOptionsMenu(navigationView.getMenu().findItem(item));
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


    public void selectOptionsMenu(MenuItem menuItem) {
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
                startActivity(new Intent(this, CursoActivity.class));
                break;

            case R.id.nav_drawer_config:
                startActivity(new Intent(this, SettingsActivity.class));
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
