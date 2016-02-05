package ufms.br.com.ufmsapp.preferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NotifyUserPreference {

    public static final String NOTIFY_USER = "notifyUser";

    SharedPreferences prefs;
    Context context;
    SharedPreferences.Editor editor;

    public NotifyUserPreference(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setNotifyUser(int notifyKey) {
        editor = prefs.edit();
        editor.putInt(NOTIFY_USER, notifyKey);
        editor.apply();
    }

    public int getNotifyUser() {
        return prefs.getInt(NOTIFY_USER, -1);
    }
}
