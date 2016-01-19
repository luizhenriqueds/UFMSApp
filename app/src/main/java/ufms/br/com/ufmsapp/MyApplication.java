package ufms.br.com.ufmsapp;

import android.app.Application;
import android.content.Context;

import ufms.br.com.ufmsapp.data.AppDAO;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static AppDAO mDatabase;

    public synchronized static AppDAO getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new AppDAO(getAppContext());
        }
        return mDatabase;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
