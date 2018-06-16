package cav.reminder.data.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import cav.reminder.data.TodoSpecModel;
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
        newValue.put("rec_date", Func.dateToStr(record.getDate(),"yyyy-MM-dd"));
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
        database.delete(DBHelper.TABLE_TODO_SPEC,"_id="+id_record,null);
        close();
    }
    // обновить запись
    public void updateRecord(RecordHeaderRes record){
        Log.d(TAG,"ID : "+Integer.toString(record.getId()));
        ContentValues updValue = new ContentValues();
        updValue.put("short_name",record.getHeaderRec());
        updValue.put("msg_body",record.getBodyRec());
        updValue.put("rec_date",Func.dateToStr(record.getDate(),"yyyy-MM-dd"));
        updValue.put("close_rec",record.getCloseRec());
        updValue.put("pass_rec",record.getPassHash());

        Log.d(TAG,"CLOSE REC : " +Integer.toString(record.getCloseRec()));

        open();
        database.update(DBHelper.TABLE_REMINDER,updValue,"_id="+record.getId(),null);
        close();
    }

    // Список дел
    public int addToDoRec(RecordHeaderRes record, ArrayList<TodoSpecModel> model){
        open();
        ContentValues values = new ContentValues();
        values.put("short_name",record.getHeaderRec());
        values.put("type_rec",1);
        values.put("rec_date", Func.dateToStr(record.getDate(),"yyyy-MM-dd"));
        values.put("todo_count",model.size());
        if (record.getId() != -1){
            values.put("_id",record.getId());
        }
        long id = database.insertWithOnConflict(DBHelper.TABLE_REMINDER,null,values,SQLiteDatabase.CONFLICT_REPLACE);

        for (int i=0;i<model.size();i++){
            values.clear();
            values.put("_id",id);
            values.put("position_id",i);
            values.put("todo_title",model.get(i).getName());
            values.put("done_flg",model.get(i).isCheck());
            //values.put("done_flg");
            database.insertWithOnConflict(DBHelper.TABLE_TODO_SPEC,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
        return (int) id;
    }

    // получить список дел
    public ArrayList<TodoSpecModel> getToDoRec(int recID) {
        ArrayList<TodoSpecModel> rec =new ArrayList<>();
        open();
        Cursor cursor = database.query(DBHelper.TABLE_TODO_SPEC,
                new String [] {"position_id","todo_title","alarm_date","alarm_time","done_flg"},
                "_id="+recID,null,null,null,"position_id");
        while (cursor.moveToNext()){
            rec.add(new TodoSpecModel(cursor.getInt(cursor.getColumnIndex("position_id")),
                    cursor.getString(cursor.getColumnIndex("todo_title")),
                    (cursor.getInt(cursor.getColumnIndex("done_flg")) == 1 ? true : false)));
        }
        close();
        return rec;
    }

    // ставим будильник
    public void setAlarm(int id,int position_id,Date alarmDate){
        open();
        ContentValues values = new ContentValues();
        values.put("alarm_date",Func.dateToStr(alarmDate,"yyyy-MM-dd"));
        values.put("alarm_time",Func.dateToStr(alarmDate,"HH:mm"));
        database.update(DBHelper.TABLE_TODO_SPEC,values,"_id="+id+" and  position_id="+position_id,null);
        close();
    }

}
