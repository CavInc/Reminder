package cav.reminder.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;

import cav.reminder.data.manager.DataManager;
import cav.reminder.data.storage.model.TodoSpecModel;

public class StartAlarmInReboot extends Service {
    private DataManager mDataManager;

    public StartAlarmInReboot() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int NOTIFICATION_ID = (int) (System.currentTimeMillis()%10000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, new Notification.Builder(this).build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        restartAlarm();
        return START_NOT_STICKY;
    }

    private void restartAlarm() {
        ArrayList<TodoSpecModel> data = mDataManager.getDataBaseConnector().getActiveAlarm();
        Toast.makeText(mDataManager.getContext(),"Start Alram",Toast.LENGTH_LONG).show();
        for (TodoSpecModel lx :data){
            Toast.makeText(mDataManager.getContext(),lx.getName(),Toast.LENGTH_LONG).show();
        }
    }
}
