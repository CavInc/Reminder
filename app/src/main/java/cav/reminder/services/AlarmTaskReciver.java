package cav.reminder.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import cav.reminder.utils.ConstantManager;

public class AlarmTaskReciver extends BroadcastReceiver {
    private static final String TAG = "ATR";
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
        Log.d(TAG,"ALARM REST");
        setNotification(context);
    }

    private void setNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = null;

        Intent intent = new Intent();

        PendingIntent pi = PendingIntent.getActivity(context,mRecID,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentIntent(pi)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Внимание !!!")
                .setTicker(mName)
                .setContentText(mName)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_SOUND);

        if (Build.VERSION.SDK_INT < 16){
            notification = builder.getNotification(); // до API 16
        }else{
            notification = builder.build();
        }
        notificationManager.notify(ConstantManager.NOTIFY_ID_F+mPosID, notification);
    }
}
