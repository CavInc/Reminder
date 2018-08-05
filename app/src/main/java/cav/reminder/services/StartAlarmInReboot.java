package cav.reminder.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StartAlarmInReboot extends Service {
    public StartAlarmInReboot() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        restartAlarm();
        return START_NOT_STICKY;
    }

    private void restartAlarm() {

    }
}
