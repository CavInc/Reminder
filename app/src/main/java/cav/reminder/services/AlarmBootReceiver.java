package cav.reminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBootReceiver extends BroadcastReceiver {
    public AlarmBootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //TODO сервис перезапускающий будильники
        }
    }
}
