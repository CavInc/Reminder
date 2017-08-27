package cav.reminder.data.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.data.database.DBHelper;
import cav.reminder.utils.Func;

/**
 * Created by cav on 09.10.16.
 */
public class DataBaseConnector {

    private String TAG="REMINDER_DBCONNECTOR";

    private SQLiteDatabase database;
    private DBHelper mDBHelper;

    public DataBaseConnector(Context context){
        mDBHelper = new DBHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
    }

    public void open(){
        database = mDBHelper.getWritableDatabase();
    }
    public  void close(){
        if (database!=null) {
            database.close();
        }
    }

    // Возвращаем все записи
    public Cursor getAllRecord (){
        return database.query(DBHelper.TABLE_REMINDER,
                new String []{"_id","short_name","rec_date","msg_body","photo_file",
                        "close_rec","pass_rec","type_rec","todo_done_count","todo_count"},
                null,null,null,null,"rec_date");
    }
    // вернуть запись по _id
    public RecordHeaderRes getRecord(int id){
        RecordHeaderRes rec=null;


        return rec;
    }

    // добавить новую запись
    public int insertRecord(RecordHeaderRes record){
        ContentValues newValue = new ContentValues();
        newValue.put("short_name",record.getHeaderRec());
        newValue.put("rec_date", Func.dateToStr(record.getDate()));
        newValue.put("msg_body",record.getBodyRec());
        newValue.put("close_rec",record.getCloseRec());
        newValue.put("pass_rec",record.getPassHash());
        newValue.put("photo_file",record.getPhotoFile());
        open();
        long id =database.insert(DBHelper.TABLE_REMINDER,null,newValue);
        Log.d(TAG,"REC ? "+Long.toString(id));
        close();
        return (int) id;
    }
    // удалить запись
    public void deleteRecord(int id_record){
        open();
        database.delete(DBHelper.TABLE_REMINDER,"_id="+id_record,null);
        close();
    }
    // обновить запись
    public void updateRecord(RecordHeaderRes record){
        Log.d(TAG,"ID : "+Integer.toString(record.getId()));
        ContentValues updValue = new ContentValues();
        updValue.put("short_name",record.getHeaderRec());
        updValue.put("msg_body",record.getBodyRec());
        updValue.put("rec_date",Func.dateToStr(record.getDate()));
        updValue.put("close_rec",record.getCloseRec());
        updValue.put("pass_rec",record.getPassHash());

        Log.d(TAG,"CLOSE REC : " +Integer.toString(record.getCloseRec()));

        open();
        database.update(DBHelper.TABLE_REMINDER,updValue,"_id="+record.getId(),null);
        close();
    }

}
