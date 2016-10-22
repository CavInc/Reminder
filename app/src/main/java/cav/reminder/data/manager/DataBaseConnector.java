package cav.reminder.data.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cav.reminder.data.RecordHeaderRes;
import cav.reminder.data.database.DBHelper;
import cav.reminder.utils.Func;

/**
 * Created by cav on 09.10.16.
 */
public class DataBaseConnector {
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
                new String []{"_id","short_name","rec_date","msg_body"},null,null,null,null,"rec_date");
    }

    // добавить новую запись
    public void insertRecord(RecordHeaderRes record){
        ContentValues newValue = new ContentValues();
        newValue.put("short_name",record.getHeaderRec());
        newValue.put("rec_date", Func.dateToStr(record.getDate()));
        newValue.put("msg_body",record.getBodyRec());
        open();
        database.insert(DBHelper.TABLE_REMINDER,null,newValue);
        close();
    }
    // удалить запись
    public void deleteRecord(int id_record){

    }

}
