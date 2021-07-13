package cav.reminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cav.reminder.data.manager.DataManager;
import cav.reminder.data.storage.model.TodoSpecModel;
import cav.reminder.ui.activites.TodoActivity;
import cav.reminder.utils.Func;

import static android.content.ContentValues.TAG;

public class AlarmBootReceiver extends BroadcastReceiver {
    private DataManager mDataManager;
    public AlarmBootReceiver() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //TODO сервис перезапускающий будильники
            /*
            Intent startAlarm = new Intent(context,StartAlarmInReboot.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startAlarm);
            } else {
                context.startService(startAlarm);
            }
            */
            ArrayList<TodoSpecModel> data = mDataManager.getDataBaseConnector().getActiveAlarm();
            Toast.makeText(mDataManager.getContext(),"Start Alram",Toast.LENGTH_LONG).show();
            Log.d(TAG,"START ALARM");
            for (TodoSpecModel lx :data){
                Toast.makeText(mDataManager.getContext(),lx.getName(),Toast.LENGTH_LONG).show();
                Func.addAlert(mDataManager.getContext(),lx.getDate(),lx,lx.getRecId());
            }
        }
    }
}
