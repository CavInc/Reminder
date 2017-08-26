package cav.reminder.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cav on 17.09.16.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_REMINDER = "REMINDER";
    public static final String TABLE_TODO_SPEC ="TODO_SPEC";
    public static final String TABLE_ALARM = "ALARM";

    public static final String DATABASE_NAME = "reminder.db3";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db,0,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db,oldVersion,newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        // нет данных в базе
        if (oldVersion<1){
            db.execSQL("create table "+TABLE_REMINDER+"" +
                    "(_id integer not null primary key AUTOINCREMENT," +
                    "short_name text," + // title
                    "msg_body text," +  // subject body
                    "photo_file text," + // path to photo
                    "rec_avd_file text," + // path to audio
                    "rec_date text," +  // data record
                    "close_rec integer default 0, "+ // close rec
                    "pass_rec text,"+ // pass close rec
                    "type_rec integer default 0,"+ // 0 - напоминалка 1- todo
                    "todo_done_count integer default 0,"+
                    "todo_count integer default 0 )");

            db.execSQL("create table "+TABLE_TODO_SPEC+""+
                    "(_id integer not null, "+
                    " position_id integer not null," +
                    " todo_title text,"+
                    " done_flg boolean default 0,primary key(_id,position_id)"+")");

            db.execSQL("create table "+TABLE_ALARM+" "+
                    "(item_id integer not null)");


        }else {
            /*
            db.execSQL("alter table "+TABLE_REMINDER+""+
            " add column close_rec integer;" );
            */

        }

    }
}
