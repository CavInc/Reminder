package cav.reminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmBootReceiver extends BroadcastReceiver {
    public AlarmBootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //TODO сервис перезапускающий будильники
            Intent startAlarm = new Intent(context,StartAlarmInReboot.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startAlarm);
            } else {
                context.startService(startAlarm);
            }
        }
    }
}
