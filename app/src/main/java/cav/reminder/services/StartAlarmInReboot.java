package cav.reminder.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;

import cav.reminder.data.TodoSpecModel;
import cav.reminder.data.manager.DataManager;

public class StartAlarmInReboot extends Service {
    private DataManager mDataManager;

    public StartAlarmInReboot() {
        mDataManager = DataManager.getInstance(getBaseContext());
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
        ArrayList<TodoSpecModel> data = mDataManager.getDataBaseConnector().getActiveAlarm();
        Toast.makeText(mDataManager.getContext(),"Start Alram",Toast.LENGTH_LONG).show();
    }
}
