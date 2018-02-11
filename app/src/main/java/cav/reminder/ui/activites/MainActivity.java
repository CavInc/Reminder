package cav.reminder.ui.activites;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


import cav.reminder.R;
import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.data.manager.DataManager;
import cav.reminder.ui.adapters.DataAdapter;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private String TAG ="REMINDER_MAIN";

    ListView mListView;
    private FloatingActionButton newButton;

    private FloatingActionButton mNewRecord;
    private FloatingActionButton mNewTodo;

    // animation fab
    Animation show_fab_record;
    Animation hide_fab_record;
    Animation show_fab_todo;
    Animation hide_fab_todo;

    private boolean statusFab = false;

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

        mNewRecord = (FloatingActionButton) findViewById(R.id.fab_new_record);
        mNewTodo = (FloatingActionButton) findViewById(R.id.fab_new_todo);
        mNewRecord.setOnClickListener(this);
        mNewTodo.setOnClickListener(this);

        // animation
        show_fab_record =  AnimationUtils.loadAnimation(getApplication(), R.anim.fab_new_rec_show);
        hide_fab_record = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_rec_hide);
        show_fab_todo = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_todo_show);
        hide_fab_todo = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_todo_hide);

        ArrayList<RecordHeaderRes> record = mDataManager.getAllRecord();

        mAdapter = new DataAdapter(this,R.layout.main_item_list,record);
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(itemLongListener);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE){
                    newButton.show();
                    flag = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (flag /*newButton.isShown()*/){
                    newButton.hide();
                    flag = false;
                }
            }
        });
    }
    private boolean flag = false;

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
            onBackPressed();
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
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.newButton:
                if (!statusFab) {
                    showFABMenu();
                    statusFab = true;
                } else {
                    hideFABMenu();
                    statusFab = false;
                }
                break;
            case R.id.edit_laout:
                Log.d(TAG,"EDIT");
                dialog.cancel();
                if (mItem.getTypeRec() == ConstantManager.TYPE_REC_MEMO) {
                    intent = new Intent(MainActivity.this, ItemActivity.class);
                    intent.putExtra(ConstantManager.MODE_RECORD, ConstantManager.MODE_EDIT_RECORD);
                    intent.putExtra(ConstantManager.RECORD_ID, mItem.getId());
                    intent.putExtra(ConstantManager.RECORD_HEADER, mItem.getHeaderRec());
                    intent.putExtra(ConstantManager.RECORD_BODY, mItem.getBodyRec());
                    intent.putExtra(ConstantManager.RECORD_CLOSE, mItem.isCloseRec());
                    intent.putExtra(ConstantManager.RECORD_PASS_SAVE, mItem.getPassHash());
                    intent.putExtra(ConstantManager.RECORD_PHOTO_FILE, mItem.getPhotoFile());

                    startActivityForResult(intent, ConstantManager.ITEM_ACTIVITY_EDIT);
                } else {
                    intent = new Intent(MainActivity.this,TodoActivity.class);
                    intent.putExtra(ConstantManager.MODE_RECORD, ConstantManager.MODE_EDIT_RECORD);
                    intent.putExtra(ConstantManager.RECORD_ID, mItem.getId());
                    intent.putExtra(ConstantManager.RECORD_HEADER, mItem.getHeaderRec());

                    startActivityForResult(intent,ConstantManager.ITEM_TODO_EDIT);
                }

                break;
            case R.id.del_laout:
                dialog.dismiss();
                mDataManager.getDataBaseConnector().deleteRecord(mItem.getId());
                mAdapter.remove(mItem);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.fab_new_record:
               // hideFABMenu();
                addNewRecord();
                break;
            case R.id.fab_new_todo:
                addNewTODO();
                break;

        }

    }

    // показать float button menu
    private void showFABMenu(){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mNewRecord.getLayoutParams();
        layoutParams.rightMargin += (int) (mNewRecord.getWidth() * 0.8);
        layoutParams.bottomMargin += (int) (mNewRecord.getHeight() * 0.25);
        mNewRecord.setLayoutParams(layoutParams);
        mNewRecord.setAnimation(show_fab_record);
        mNewRecord.setClickable(true);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) mNewTodo.getLayoutParams();
        layoutParams2.rightMargin += (int) (mNewTodo.getWidth() * 0.25);
        layoutParams2.bottomMargin += (int) (mNewTodo.getHeight() * 0.7);
        mNewTodo.setLayoutParams(layoutParams2);
        mNewTodo.startAnimation(show_fab_todo);
        mNewTodo.setClickable(true);
    }
    // скрыть float button menu
    private void hideFABMenu(){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mNewRecord.getLayoutParams();
        layoutParams.rightMargin -= (int) (mNewRecord.getWidth() * 0.8);
        layoutParams.bottomMargin -= (int) (mNewRecord.getHeight() * 0.25);
        mNewRecord.setLayoutParams(layoutParams);
        mNewRecord.setAnimation(hide_fab_record);
        mNewRecord.setClickable(false);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) mNewTodo.getLayoutParams();
        layoutParams2.rightMargin -= (int) (mNewTodo.getWidth() * 0.25);
        layoutParams2.bottomMargin -= (int) (mNewTodo.getHeight() * 0.7);
        mNewTodo.setLayoutParams(layoutParams2);
        mNewTodo.startAnimation(hide_fab_todo);
        mNewTodo.setClickable(false);

    }

    // добавляем новую заметку
    private void addNewRecord(){
        Intent intent = new Intent(MainActivity.this,ItemActivity.class);
        intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_INS_RECORD);
        // startActivity(intent);
        startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_NEW);
    }

    // добавляем новое TO DO
    private void addNewTODO(){
        Intent intent = new Intent(this,TodoActivity.class);
        startActivityForResult(intent,ConstantManager.ITEM_TODO_NEW);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (statusFab) {
            hideFABMenu();
            statusFab = false;
        }

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
                    String x = data.getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA),"yyyy-MM-dd"),
                            data.getStringExtra(ConstantManager.LONG_DATA),data.getStringExtra(ConstantManager.RECORD_PHOTO_FILE),
                            data.getBooleanExtra(ConstantManager.RECORD_CLOSE,false),
                            data.getStringExtra(ConstantManager.RECORD_PASS_SAVE));
                    int id = mDataManager.getDataBaseConnector().insertRecord(lrecord);
                    lrecord.setId(id);
                    mAdapter.insert(lrecord,0);
                    //mAdapter.add(lrecord);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ConstantManager.ITEM_ACTIVITY_EDIT:
                Log.d(TAG,"RETURN EDIT");
                if (resultCode == RESULT_OK && data !=null){
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getIntExtra(ConstantManager.RECORD_ID,-1),
                            data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA),"yyyy-MM-dd"),
                            data.getStringExtra(ConstantManager.LONG_DATA),data.getStringExtra(ConstantManager.RECORD_PHOTO_FILE),
                            data.getBooleanExtra(ConstantManager.RECORD_CLOSE,false),
                            data.getStringExtra(ConstantManager.RECORD_PASS_SAVE));
                    Log.d(TAG+" EDIT: ",data.getStringExtra(ConstantManager.LONG_DATA));
                    mDataManager.getDataBaseConnector().updateRecord(lrecord);
                    Log.d(TAG +" EDIT POS: ",Integer.toString(mAdapter.getPosition(mItem)));
                    int id = mAdapter.getPosition(mItem);
                    mAdapter.remove(mItem);
                    mAdapter.insert(lrecord,id);

                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ConstantManager.ITEM_ACTIVITY_VIEW:
                Log.d(TAG,"RETURN VIEW");
                break;
            //"To Do" ac
            case ConstantManager.ITEM_TODO_NEW:
                if (resultCode == RESULT_OK && data !=null){
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA),"yyyy-MM-dd"),
                            data.getStringExtra(ConstantManager.LONG_DATA),
                            data.getStringExtra(ConstantManager.RECORD_PHOTO_FILE),
                            data.getBooleanExtra(ConstantManager.RECORD_CLOSE,false),
                            data.getStringExtra(ConstantManager.RECORD_PASS_SAVE));
                    lrecord.setTypeRec(ConstantManager.TYPE_REC_TODO);
                    lrecord.setId(data.getIntExtra(ConstantManager.RECORD_ID,-1));
                    lrecord.setAllTodoCount(data.getIntExtra(ConstantManager.TODO_COUNT_SIZE,0));
                    lrecord.setDoneCount(data.getIntExtra(ConstantManager.TODO_DONE_COUNT,0));
                    mAdapter.insert(lrecord,0);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ConstantManager.ITEM_TODO_EDIT:
                if (resultCode == RESULT_OK && data !=null){
                    RecordHeaderRes lrecord = new RecordHeaderRes(
                            data.getIntExtra(ConstantManager.RECORD_ID,-1),
                            data.getStringExtra(ConstantManager.SHORT_DATA),
                            Func.strToDate(data.getStringExtra(ConstantManager.DATE_DATA),"yyyy-MM-dd"),
                            data.getStringExtra(ConstantManager.LONG_DATA),
                            data.getStringExtra(ConstantManager.RECORD_PHOTO_FILE),
                            data.getBooleanExtra(ConstantManager.RECORD_CLOSE,false),
                            data.getStringExtra(ConstantManager.RECORD_PASS_SAVE));
                    lrecord.setTypeRec(ConstantManager.TYPE_REC_TODO);
                    lrecord.setAllTodoCount(data.getIntExtra(ConstantManager.TODO_COUNT_SIZE,0));
                    lrecord.setDoneCount(data.getIntExtra(ConstantManager.TODO_DONE_COUNT,0));
                    int id = mAdapter.getPosition(mItem);
                    mAdapter.remove(mItem);
                    mAdapter.insert(lrecord,id);

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
            //TODO сделать вызов активити для прсотомра и редактирования в методе и убрать откуда не надо
            Log.d(TAG, "CLICK");
            if (statusFab) {
                hideFABMenu();
                statusFab = false;
            }
            mItem = mAdapter.getItem(position);
            // обычная запись
            if (mItem.getTypeRec() == ConstantManager.TYPE_REC_MEMO) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_RECORD, ConstantManager.MODE_VIEW_RECORD);
                intent.putExtra(ConstantManager.RECORD_ID, mItem.getId());
                intent.putExtra(ConstantManager.RECORD_HEADER, mItem.getHeaderRec());
                intent.putExtra(ConstantManager.RECORD_BODY, mItem.getBodyRec());
                intent.putExtra(ConstantManager.RECORD_CLOSE, mItem.isCloseRec());
                intent.putExtra(ConstantManager.RECORD_PASS_SAVE, mItem.getPassHash());
                intent.putExtra(ConstantManager.RECORD_PHOTO_FILE, mItem.getPhotoFile());
                startActivityForResult(intent, ConstantManager.ITEM_ACTIVITY_VIEW);
            }
            // запись с 'to do'
            if (mItem.getTypeRec() == ConstantManager.TYPE_REC_TODO) {
                Intent intent = new Intent(MainActivity.this,TodoActivity.class);
                intent.putExtra(ConstantManager.MODE_RECORD,ConstantManager.MODE_VIEW_RECORD);
                intent.putExtra(ConstantManager.RECORD_ID, mItem.getId());
                intent.putExtra(ConstantManager.RECORD_HEADER, mItem.getHeaderRec());

                startActivity(intent);
            }
        }

    };



}
