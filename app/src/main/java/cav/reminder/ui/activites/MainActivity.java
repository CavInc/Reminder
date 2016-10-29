package cav.reminder.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


import cav.reminder.R;
import cav.reminder.data.RecordHeaderRes;
import cav.reminder.data.database.DBHelper;
import cav.reminder.data.manager.DataManager;
import cav.reminder.ui.adapters.DataAdapter;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private String TAG ="REMINDER_MAIN";

    ListView mListView;
    private ImageView newButton;

    private DataAdapter mAdapter;

    private DataManager mDataManager;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setDataBase();
        mDataManager = DataManager.getInstance(this);

        mListView = (ListView) findViewById(R.id.listView);
        newButton = (ImageView) findViewById(R.id.newButton);
        newButton.setOnClickListener(this);
/*
        // отладочные данные
        ArrayList<RecordHeaderRes> record = new ArrayList<>();
        record.add(new RecordHeaderRes("Яблоки",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"));
        record.add(new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2016"),"Таблетки от забывчивости"));
        record.add(new RecordHeaderRes("А вот прикольное фото",strToDate("23.08.2016"),"Фотка в парке "));
        record.add(new RecordHeaderRes("Яблоки",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"));
        record.add(new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2016"),"Таблетки от забывчивости"));
        record.add(new RecordHeaderRes("А вот прикольное фото",strToDate("23.08.2015"),"Фотка в парке "));
        record.add(new RecordHeaderRes("Яблоки fd",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"));
        record.add(new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2015"),"Таблетки от забывчивости"));
        record.add(new RecordHeaderRes("А вот прикольное фото2",strToDate("23.08.2015"),"Фотка в парке "));
*/
        ArrayList<RecordHeaderRes> record = mDataManager.getAllRecord();

        mAdapter = new DataAdapter(this,R.layout.main_item_list,record);
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(itemLongListener);


    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"RESUME");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"PAUSE");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newButton:
                showToast("Новая запись");
                Intent intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_INS_RECORD,true);
               // startActivity(intent);
                startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_NEW);
                break;

        }

    }

    private void setDataBase() {
        db = openOrCreateDatabase(DBHelper.DATABASE_NAME, Context.MODE_PRIVATE,
                null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ConstantManager.ITEM_ACTIVITY_NEW:
                Log.d(TAG,Integer.toString(resultCode));
                System.out.println(data!=null);
                if (resultCode == RESULT_OK && data !=null){
                    Log.d(TAG,data.getStringExtra(ConstantManager.SHORT_DATA));
                    System.out.println(data.getStringExtra(ConstantManager.LONG_DATA));
                    Log.d(TAG,data.getStringExtra(ConstantManager.DATE_DATA));
/*
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getStringExtra(ConstantManager.SHORT_DATA),
                            strToDate(data.getStringExtra(ConstantManager.DATE_DATA)),
                            data.getStringExtra(ConstantManager.LONG_DATA));
                    mAdapter.add(lrecord);
*/
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA)),
                            data.getStringExtra(ConstantManager.LONG_DATA));
                    mDataManager.getDataBaseConnector().insertRecord(lrecord);
                    mAdapter.add(lrecord);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
       // super.onActivityResult(requestCode, resultCode, data);
    }

    AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            showToast("Long click in item "+Integer.toString(position));
            System.out.println(adapterView.getSelectedItem());
            System.out.println(adapterView.getClass());
            System.out.println(mAdapter.getItem(position).getHeaderRec());
            return true;
        }
    };



    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Log.d(TAG, "CLICK");
            System.out.println(adapterView.getSelectedItem());
            System.out.println(mAdapter.getItem(position).getHeaderRec());
        }

    };



}
