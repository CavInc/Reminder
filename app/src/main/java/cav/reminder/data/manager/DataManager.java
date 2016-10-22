package cav.reminder.data.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cav.reminder.data.RecordHeaderRes;
import cav.reminder.ui.activites.MainActivity;
import cav.reminder.utils.Func;

public class DataManager {
    private String TAG ="REMINDER_DATAMANAGER";

    private static DataManager INSTANCE = null;

    private PreferensManager mPreferensManager;
    private DataBaseConnector mDbc;

    public DataManager(Context context) {
        this.mPreferensManager = new PreferensManager();
        this.mDbc = new DataBaseConnector(context);
    }

    public static DataManager getInstance(Context context) {
        if (INSTANCE==null){
            INSTANCE = new DataManager(context);
        }
        return INSTANCE;
    }

    public DataBaseConnector getDataBaseConnector(){
        return this.mDbc;
    }

    public ArrayList<RecordHeaderRes> getAllRecord (){
        Log.d(TAG,"ALL RECORD ");
        ArrayList<RecordHeaderRes> record = new ArrayList<>();
        this.mDbc.open();
        Cursor cursor = this.mDbc.getAllRecord();
        while (cursor.moveToNext()){
            cursor.getInt(cursor.getColumnIndex("_id"));
            Log.d(TAG,cursor.getString(cursor.getColumnIndex("short_name")));
            record.add(new RecordHeaderRes(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("short_name")),
                    Func.strToDate(cursor.getString(cursor.getColumnIndex("rec_date"))),
                    cursor.getString(cursor.getColumnIndex("msg_body"))));
        }

        this.mDbc.close();
        return record;
    }

}
