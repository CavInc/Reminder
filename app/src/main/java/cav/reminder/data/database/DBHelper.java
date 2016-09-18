package cav.reminder.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cav on 17.09.16.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1 ;


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

        }

    }
}
