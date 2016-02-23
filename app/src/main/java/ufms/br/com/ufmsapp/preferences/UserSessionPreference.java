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

package ufms.br.com.ufmsapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class UserSessionPreference {

    private SharedPreferences prefs;

    private Editor editor;

    final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UserLogin";

    private static final String IS_FIRST_TIME = "IsFirstTime";

    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    Context mContext;

    public UserSessionPreference(Context context) {
        this.mContext = context;
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public boolean isFirstTime() {
        return prefs.getBoolean(IS_FIRST_TIME, true);
    }

    public void setOld(boolean b) {
        if (b) {
            editor.putBoolean(IS_FIRST_TIME, false);
            editor.apply();
        }
    }

    public void logOut() {
        editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public String getName() {
        return prefs.getString(USER_NAME, "");
    }

    public String getEmail() {
        return prefs.getString(USER_EMAIL, "");
    }

    public void setName(String name) {
        editor = prefs.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }

    public void setEmail(String email) {
        editor = prefs.edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

}
