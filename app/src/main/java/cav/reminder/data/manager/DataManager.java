package cav.reminder.data.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.utils.App;
import cav.reminder.utils.Func;

public class DataManager {
    private final String TAG ="REM_DM";

    private static DataManager INSTANCE = null;

    private final PreferensManager mPreferensManager;
    private final DataBaseConnector mDbc;
    private final Context mContext;

    private RecordHeaderRes mRecordHeaderRes;

    public DataManager() {
        mContext = App.getContext();
        this.mPreferensManager = new PreferensManager();
        this.mDbc = new DataBaseConnector(mContext);

    }

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public DataBaseConnector getDataBaseConnector(){
        return this.mDbc;
    }

    public ArrayList<RecordHeaderRes> getAllRecord (){
        ArrayList<RecordHeaderRes> record = new ArrayList<>();
        this.mDbc.open();
        Cursor cursor = this.mDbc.getAllRecord();
        while (cursor.moveToNext()){
            Log.d(TAG,cursor.getString(cursor.getColumnIndexOrThrow("short_name")));
            // выбираем фотки
            ArrayList<String> photos_array = new ArrayList<>();
            Cursor photos = mDbc.getPhotosIdRecord(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            while (photos.moveToNext()){
                photos_array.add(photos.getString(photos.getColumnIndexOrThrow("photo_file")));
            }
            record.add(new RecordHeaderRes(cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("short_name")),
                    Func.strToDate(cursor.getString(cursor.getColumnIndexOrThrow("rec_date")),"yyyy-MM-dd"),
                    cursor.getString(cursor.getColumnIndexOrThrow("msg_body")),
                    cursor.getString(cursor.getColumnIndexOrThrow("photo_file")),
                    (cursor.getInt(cursor.getColumnIndexOrThrow("close_rec"))==1),
                    cursor.getString(cursor.getColumnIndexOrThrow("pass_rec")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("type_rec")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("todo_count")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("todo_done_count")),
                    photos_array
                    ));

        }
        this.mDbc.close();
        return record;
    }

    public Context getContext() {
        return mContext;
    }

    public PreferensManager getPreferensManager() {
        return mPreferensManager;
    }

    // включена ли сеть
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo !=null && netInfo.isConnectedOrConnecting();
    }

    public RecordHeaderRes getRecordHeaderRes() {
        return mRecordHeaderRes;
    }

    public void setRecordHeaderRes(RecordHeaderRes recordHeaderRes) {
        mRecordHeaderRes = recordHeaderRes;
    }
}
