package cav.reminder.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cav.reminder.utils.ConstantManager;

public class AlarmTaskReciver extends BroadcastReceiver {
    private Context mContext;
    private int mRecID;
    private String mName;
    private int mPosID;

    public AlarmTaskReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mRecID = intent.getIntExtra(ConstantManager.TODO_REC_ID,-1);
        mPosID = intent.getIntExtra(ConstantManager.TODO_POS_ID,-1);
        mName = intent.getStringExtra(ConstantManager.TODO_REC_NAME);
        setNotification();
    }

    private void setNotification(){
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = null;

        Notification.Builder builder = new Notification.Builder(mContext);

    }
}
