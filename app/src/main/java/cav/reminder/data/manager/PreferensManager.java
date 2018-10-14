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

    public int getAlarmRingtone(){
        return mSharedPreferences.getInt("alarm_ringtone",-1);
    }
}
