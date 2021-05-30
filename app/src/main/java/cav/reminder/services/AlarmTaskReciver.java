package cav.reminder.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import cav.reminder.R;

import cav.reminder.data.manager.DataManager;
import cav.reminder.ui.activites.TodoActivity;
import cav.reminder.utils.ConstantManager;

public class AlarmTaskReciver extends BroadcastReceiver {
    private static final String TAG = "ATR";
    private static final String CHANEL_ID = "cav.reminder.Reminder";
    private Context mContext;
    private int mRecID;
    private String mName;
    private int mPosID;
    private DataManager mDataManager;

    public AlarmTaskReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mRecID = intent.getIntExtra(ConstantManager.TODO_REC_ID,-1);
        mPosID = intent.getIntExtra(ConstantManager.TODO_POS_ID,-1);
        mName = intent.getStringExtra(ConstantManager.TODO_REC_NAME);
        mDataManager = DataManager.getInstance();
        Log.d(TAG,"ALARM REST");
        DataManager.getInstance().getDataBaseConnector().closeAlarm(mRecID,mPosID);
        setNotification(context);
    }


    private void setNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //для А8+
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,"Reminder",NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Reminder");
            channel.enableLights(true);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = null;

        Intent intent = new Intent(context, TodoActivity.class);
        intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_VIEW_RECORD);
        intent.putExtra(ConstantManager.RECORD_ID,mRecID);
        intent.putExtra(ConstantManager.RECORD_POS_ID,mPosID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Для полного запуска последовательности
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(TodoActivity.class);
        stackBuilder.addNextIntent(intent);


        //PendingIntent pi = PendingIntent.getActivity(mContext,mRecID+mPosID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pi = stackBuilder.getPendingIntent(mRecID+mPosID,PendingIntent.FLAG_UPDATE_CURRENT);

        //Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri ringURI = Uri.parse(mDataManager.getPreferensManager().getRingtone());

        Log.d(TAG,"String alarm :"+mDataManager.getPreferensManager().getRingtone());


        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder = new Notification.Builder(mContext,CHANEL_ID);
        } else {
            builder = new Notification.Builder(mContext);
        }

        builder.setContentIntent(pi)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(mContext.getString(R.string.dialog_title_warning))
                .setTicker(mContext.getString(R.string.dialog_title_warning))
                .setContentText(mName)
                .setOngoing(true)
                .setAutoCancel(true)
                .setSound(ringURI);

        if (Build.VERSION.SDK_INT < 16){
            notification = builder.getNotification(); // до API 16
        }else{
            notification = builder.build();
        }
        notificationManager.notify(ConstantManager.NOTIFY_ID_F+mRecID+mPosID, notification);
    }
}
