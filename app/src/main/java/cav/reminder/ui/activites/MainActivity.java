package cav.reminder.ui.activites;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;


import cav.reminder.R;
import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.data.manager.DataManager;
import cav.reminder.ui.adapters.DataAdapter;
import cav.reminder.ui.dialogs.EditDeleteDialog;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final int REQUEST_WRITER = 845;
    private String TAG ="REMINDER_MAIN";

    ListView mListView;
    //private FloatingActionButton newButton;

  //  private FloatingActionButton mNewRecord;
  //  private FloatingActionButton mNewTodo;



     // animation fab
    Animation show_fab_record;
    Animation hide_fab_record;
    Animation show_fab_todo;
    Animation hide_fab_todo;

    private FloatingActionMenu mFabMenu;

    private boolean statusFab = false;

    private DataAdapter mAdapter;

    private DataManager mDataManager;
    private RecordHeaderRes mItem=null;

        SQLiteDatabase db;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,getResources().getString(R.string.admob_id));
        mAdView = (AdView) findViewById(R.id.adView);


        //setDataBase();
        mDataManager = DataManager.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setupToolbar(toolbar);

        mListView = (ListView) findViewById(R.id.listView);
      //  newButton = (FloatingActionButton) findViewById(R.id.newButton);
      //  newButton.setOnClickListener(this);

      //  mNewRecord = (FloatingActionButton) findViewById(R.id.fab_new_record);
      //  mNewTodo = (FloatingActionButton) findViewById(R.id.fab_new_todo);
     //   mNewRecord.setOnClickListener(this);
      //  mNewTodo.setOnClickListener(this);

        // animation
      //  show_fab_record =  AnimationUtils.loadAnimation(getApplication(), R.anim.fab_new_rec_show);
     //   hide_fab_record = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_rec_hide);
     //   show_fab_todo = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_todo_show);
    //    hide_fab_todo = AnimationUtils.loadAnimation(getApplication(),R.anim.fab_new_todo_hide);
        mFabMenu = findViewById(R.id.fab_menu);

        findViewById(R.id.fab_add_item).setOnClickListener(this);
        findViewById(R.id.fab_add_todo).setOnClickListener(this);

        ArrayList<RecordHeaderRes> record = mDataManager.getAllRecord();

        mAdapter = new DataAdapter(this,R.layout.main_item_list,record);
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(itemLongListener);

        /*
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
                if (flag /*newButton.isShown()*//*){
                    newButton.hide();
                    flag = false;
                }
            }
        });
        */



        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private boolean flag = false;

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Log.d(TAG,"BACK BUTTON");
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.menu_setting) {
            Intent setting = new Intent(this,PrefActivity.class);
            startActivity(setting);
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
        if (! mDataManager.isOnline()) {
            mAdView.setVisibility(View.GONE);
        } else {
            mAdView.setVisibility(View.VISIBLE);
            if (mAdView != null) {
                mAdView.resume();
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITER);
        }
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        mFabMenu.close(true);
        Intent intent;
        switch (v.getId()){
            /*
            case R.id.newButton:
                if (!statusFab) {
                    //showFABMenu();
                    statusFab = true;
                } else {
                   // hideFABMenu();
                    statusFab = false;
                }
                break;
            */
            /*
            case R.id.fab_new_record:
               // hideFABMenu();
                addNewRecord();
                break;
            case R.id.fab_new_todo:
                addNewTODO();
                break;
                */
            case R.id.fab_add_item:
                addNewRecord();
                break;
            case R.id.fab_add_todo:
                addNewTODO();
                break;
        }

    }

    // показать float button menu
    /*
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
    */

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
           // hideFABMenu();
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
           // showToast("Long click in item "+Integer.toString(position));
            mFabMenu.close(true);
            mItem = mAdapter.getItem(position);
            viewItemDialog();
            return true;
        }
    };

    private Dialog dialog;

    private void viewItemDialog() {
        EditDeleteDialog deleteDialog = new EditDeleteDialog();
        deleteDialog.setSelectEditDeleteListener(mSelectEditDeleteListener);
        deleteDialog.show(getSupportFragmentManager(),"EDD");
    }

    EditDeleteDialog.SelectEditDeleteListener mSelectEditDeleteListener = new EditDeleteDialog.SelectEditDeleteListener() {
        @Override
        public void selectItem(int id) {
            Intent intent;
            switch (id) {
                case R.id.edit_laout:
                    Log.d(TAG,"EDIT");
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
                    mDataManager.getDataBaseConnector().deleteRecord(mItem.getId());
                    mAdapter.remove(mItem);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mFabMenu.close(true);
            //TODO сделать вызов активити для прсотомра и редактирования в методе и убрать откуда не надо
            Log.d(TAG, "CLICK");
            if (statusFab) {
              //  hideFABMenu();
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
