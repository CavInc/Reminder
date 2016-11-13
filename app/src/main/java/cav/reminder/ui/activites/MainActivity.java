package cav.reminder.ui.activites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private FloatingActionButton newButton;

    private DataAdapter mAdapter;

    private DataManager mDataManager;
    private RecordHeaderRes mItem=null;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setDataBase();
        mDataManager = DataManager.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setupToolbar(toolbar);

        mListView = (ListView) findViewById(R.id.listView);
        newButton = (FloatingActionButton) findViewById(R.id.newButton);
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

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Log.d(TAG,"BACK BUTTON");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG,"BACK PRESSED");
        super.onBackPressed();
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
        Intent intent;
        switch (v.getId()){
            case R.id.newButton:
                showToast("Новая запись");
                intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_INS_RECORD);
               // startActivity(intent);
                startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_NEW);
                break;
            case R.id.edit_laout:
                Log.d(TAG,"EDIT");
                dialog.cancel();
                System.out.println(mItem.getId());
                intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_EDIT_RECORD);
                intent.putExtra(ConstantManager.RECORD_ID,mItem.getId());
                intent.putExtra(ConstantManager.RECORD_HEADER,mItem.getHeaderRec());
                intent.putExtra(ConstantManager.RECORD_BODY,mItem.getBodyRec());

                startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_EDIT);

                break;
            case R.id.del_laout:
                dialog.dismiss();
                mDataManager.getDataBaseConnector().deleteRecord(mItem.getId());
                mAdapter.remove(mItem);
                mAdapter.notifyDataSetChanged();
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ConstantManager.ITEM_ACTIVITY_NEW:
                Log.d(TAG,Integer.toString(resultCode));
                System.out.println(data!=null);
                if (resultCode == RESULT_OK && data !=null){
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
            case ConstantManager.ITEM_ACTIVITY_EDIT:
                Log.d(TAG,"RETURN EDIT");
                if (resultCode == RESULT_OK && data !=null){
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getIntExtra(ConstantManager.RECORD_ID,-1),
                            data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA)),
                            data.getStringExtra(ConstantManager.LONG_DATA));
                    Log.d(TAG+" EDIT: ",data.getStringExtra(ConstantManager.LONG_DATA));
                    mDataManager.getDataBaseConnector().updateRecord(lrecord);

                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ConstantManager.ITEM_ACTIVITY_VIEW:
                Log.d(TAG,"RETURN VIEW");
                break;
        }
       // super.onActivityResult(requestCode, resultCode, data);
    }

    AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            showToast("Long click in item "+Integer.toString(position));
            mItem = mAdapter.getItem(position);
            viewItemDialog();
            return true;
        }
    };

    private Dialog dialog;

    private void viewItemDialog() {
        dialog = new Dialog(this);
        dialog.setTitle(R.string.dialog_title);

        dialog.setContentView(R.layout.dialog_main_item);
        LinearLayout mEditLayout = (LinearLayout) dialog.findViewById(R.id.edit_laout);
        LinearLayout mDelLayout = (LinearLayout) dialog.findViewById(R.id.del_laout);
        mEditLayout.setOnClickListener(this);
        mDelLayout.setOnClickListener(this);

        dialog.show();
    }


    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Log.d(TAG, "CLICK");
            System.out.println(adapterView.getSelectedItem());
            System.out.println(mAdapter.getItem(position).getHeaderRec());
            mItem = mAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this,ItemActivity.class);
            intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_VIEW_RECORD);
            intent.putExtra(ConstantManager.RECORD_ID,mItem.getId());
            intent.putExtra(ConstantManager.RECORD_HEADER,mItem.getHeaderRec());
            intent.putExtra(ConstantManager.RECORD_BODY,mItem.getBodyRec());

            startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_VIEW);
        }

    };



}
