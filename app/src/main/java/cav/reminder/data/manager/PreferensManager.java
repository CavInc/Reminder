package cav.reminder.data.manager;

import android.content.SharedPreferences;

import cav.reminder.utils.App;

/**
 * Created by cav on 09.10.16.
 */
public class PreferensManager {
    private SharedPreferences mSharedPreferences;

    public PreferensManager(){
        mSharedPreferences = App.getPreferences();
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}
