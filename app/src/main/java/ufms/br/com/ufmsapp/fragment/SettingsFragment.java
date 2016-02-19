package ufms.br.com.ufmsapp.fragment;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

import ufms.br.com.ufmsapp.R;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected Resources res;
    private SwitchPreference preferenceNotificationSound;
    private CheckBoxPreference preferenceNotificationEnable;
    protected Preference logoutPreference;

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

        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "LOGOUT!!!!", Toast.LENGTH_LONG).show();
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