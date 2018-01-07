package cav.reminder.data.manager;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import cav.reminder.data.storage.model.RecordHeaderRes;
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
            Log.d(TAG,cursor.getString(cursor.getColumnIndex("short_name")));
            record.add(new RecordHeaderRes(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("short_name")),
                    Func.strToDate(cursor.getString(cursor.getColumnIndex("rec_date")),"yyyy-MM-dd"),
                    cursor.getString(cursor.getColumnIndex("msg_body")),
                    cursor.getString(cursor.getColumnIndex("photo_file")),
                    (cursor.getInt(cursor.getColumnIndex("close_rec"))==1),
                    cursor.getString(cursor.getColumnIndex("pass_rec")),
                    cursor.getInt(cursor.getColumnIndex("type_rec")),
                    cursor.getInt(cursor.getColumnIndex("todo_count")),
                    cursor.getInt(cursor.getColumnIndex("todo_done_count"))));
        }
        this.mDbc.close();
        return record;
    }

}
