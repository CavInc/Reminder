package cav.reminder.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class App extends Application {

    private static Context sContext;
    private static SharedPreferences sPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getBaseContext();
        sPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
    }

    public static Context getContext() {
        return sContext;
    }

    public static SharedPreferences getPreferences() {
        return sPreferences;
    }
}