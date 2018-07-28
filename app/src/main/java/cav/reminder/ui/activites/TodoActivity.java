package cav.reminder.ui.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.data.TodoSpecModel;
import cav.reminder.data.manager.DataManager;
import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.ui.adapters.TodoAdapter;
import cav.reminder.ui.dialogs.DateTimerFragment;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener {

    private ListView mListView;
    private FloatingActionButton mFabNew;
    private EditText mName;

    private DataManager mDataManager;
    private TodoAdapter mTodoAdapter;

    private RecordHeaderRes mRecord;

    private int mode = 0;
    private int mRecID = -1;

    private Menu mMenu;
    private boolean mSetMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mDataManager = DataManager.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabNew = (FloatingActionButton) findViewById(R.id.fab_todo_add_item);

        mFabNew.setOnClickListener(this);
        mName = (EditText) findViewById(R.id.short_et);

        // получили переданное значение
        mode = getIntent().getIntExtra(ConstantManager.MODE_RECORD,-1);

        mListView = (ListView) findViewById(R.id.todo_list);

        ArrayList<TodoSpecModel> model = new ArrayList<>();

        if ((mode == ConstantManager.MODE_VIEW_RECORD) || (mode == ConstantManager.MODE_EDIT_RECORD)) {
            mName.setText(getIntent().getStringExtra(ConstantManager.RECORD_HEADER));
            mRecID = getIntent().getIntExtra(ConstantManager.RECORD_ID,-1);
            model = mDataManager.getDataBaseConnector().getToDoRec(mRecID);
        }

        mTodoAdapter = new TodoAdapter(this,R.layout.todo_item,model);
        mListView.setAdapter(mTodoAdapter);

        setupToolbar(toolbar);

        if (mode == ConstantManager.MODE_VIEW_RECORD) {
            mName.setFocusable(false);
            mFabNew.setVisibility(View.GONE);
            mListView.setOnItemLongClickListener(this);
        } else {
            mListView.setOnItemClickListener(mItemClickListener);
        }
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (mode != ConstantManager.MODE_VIEW_RECORD) {
                    saveDate();
                }
                onBackPressed();
                return true;
            case R.id.todo_setalarm:
                //TODO диалог устаовки времени
                mMenu.findItem(R.id.todo_setalarm).setVisible(false);
                DateTimerFragment dateTimerFragment = new DateTimerFragment();
                dateTimerFragment.setOnDateTimeChangeListener(mDateTimeChangeListener);
                dateTimerFragment.show(getFragmentManager(),"DT");
                return true;
        }
        return true;
    }

    private TextView mNameItem;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_todo_add_item) {
            View vl = LayoutInflater.from(this).inflate(R.layout.create_edit_todo_layout, null);
            mNameItem = (TextView) vl.findViewById(R.id.dialog_ce_toto);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Добавить заданние")
                    .setView(vl)
                    .setNegativeButton(R.string.dialog_no,null)
                    .setPositiveButton(R.string.dialog_yes,mTodoListener)
                    .create();
            dialog.show();
        }
    }

    DialogInterface.OnClickListener mTodoListener = new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialog, int which) {
            TodoSpecModel model = new TodoSpecModel(mTodoAdapter.getCount()+1,mNameItem.getText().toString(),false);
            mTodoAdapter.add(model);
            mTodoAdapter.notifyDataSetChanged();
        }
    };

    DateTimerFragment.OnDateTimeChangeListener mDateTimeChangeListener = new DateTimerFragment.OnDateTimeChangeListener() {
        @Override
        public void OnDateTimeChange(Date date) {
            Func.addAlert(TodoActivity.this,date,selectedItem,mRecID);
            mDataManager.getDataBaseConnector().setAlarm(mRecID,selectedItem.getPosition(),date);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void saveDate(){
        if (mName.getText().toString().length() != 0) {
            int checkCount = 0;
            ArrayList<TodoSpecModel> models = new ArrayList<>();
            for (int i=0;i<mTodoAdapter.getCount();i++){
                models.add(mTodoAdapter.getItem(i));
                if (mTodoAdapter.getItem(i).isCheck()){
                    checkCount += 1;
                }
            }
            mRecord = new RecordHeaderRes(mRecID,mName.getText().toString(),new Date(),"");
            int id = mDataManager.getDataBaseConnector().addToDoRec(mRecord,models);

            // сохраниили и отдали данные
            Intent answerIntent = new Intent();
            answerIntent.putExtra(ConstantManager.SHORT_DATA,mName.getText().toString());
            answerIntent.putExtra(ConstantManager.DATE_DATA, Func.dateToStr(new Date(),"yyyy-MM-dd"));
            answerIntent.putExtra(ConstantManager.RECORD_CLOSE,false);
            answerIntent.putExtra(ConstantManager.RECORD_PASS_SAVE,"");
            answerIntent.putExtra(ConstantManager.RECORD_ID,id);
            answerIntent.putExtra(ConstantManager.TODO_COUNT_SIZE,models.size());
            answerIntent.putExtra(ConstantManager.TODO_DONE_COUNT,checkCount);
            setResult(RESULT_OK, answerIntent);
        }

    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            TodoSpecModel mx = (TodoSpecModel) adapterView.getItemAtPosition(position);
            if (mx.isCheck()){
                mTodoAdapter.getItem(position).setCheck(false);
            } else {
                mTodoAdapter.getItem(position).setCheck(true);
            }
            mTodoAdapter.notifyDataSetChanged();

        }
    };

    private TodoSpecModel selectedItem;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        selectedItem = (TodoSpecModel) adapterView.getItemAtPosition(position);
        // если задание выполнено то будильник не ставим
        if (selectedItem.isCheck()) return true;

        if (!mSetMode) {
            mMenu.findItem(R.id.todo_setalarm).setVisible(true);
        } else {
            mMenu.findItem(R.id.todo_setalarm).setVisible(false);
        }
        mSetMode = !mSetMode;
        return true;
    }
}
