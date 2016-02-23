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

package ufms.br.com.ufmsapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.LicenseActivity;
import ufms.br.com.ufmsapp.activity.LoginActivity;
import ufms.br.com.ufmsapp.activity.MainActivity;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected Resources res;
    private SwitchPreference preferenceNotificationSound;
    private CheckBoxPreference preferenceNotificationEnable;
    protected Preference logoutPreference;
    protected Preference aboutLicensePreference;

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        res = getActivity().getResources();

        preferenceNotificationSound = (SwitchPreference) findPreference(res.getString(R.string.pref_notification_sound));
        preferenceNotificationEnable = (CheckBoxPreference) findPreference(res.getString(R.string.pref_notification_enable));
        logoutPreference = findPreference(res.getString(R.string.pref_logout_app));
        aboutLicensePreference = findPreference(res.getString(R.string.pref_app_license));

        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                UserSessionPreference prefs = new UserSessionPreference(getActivity());
                prefs.logOut();

                LoginActivity loginActivity = new LoginActivity();
                loginActivity.setUpdatedServidor(false);

                getActivity().finish();

                MainActivity mainActivity = new MainActivity();
                mainActivity.finish();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                return true;
            }
        });

        aboutLicensePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), LicenseActivity.class));
                return true;
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        if (preferenceNotificationEnable.isChecked()) {
            preferenceNotificationSound.setEnabled(true);
        } else {
            preferenceNotificationSound.setEnabled(false);
        }
    }

    private void setEnabled(boolean enabled) {
        preferenceNotificationSound.setChecked(enabled);
        preferenceNotificationSound.setEnabled(enabled);
        SharedPreferences.Editor editor = getPreferenceScreen().getSharedPreferences().edit();
        editor.putBoolean(res.getString(R.string.pref_notification_sound), enabled);
        editor.apply();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(preferenceNotificationEnable.getKey())) {
            boolean enabled = sharedPreferences.getBoolean(key, true);
            setEnabled(enabled);
        }
    }
}