package cav.reminder.ui.activites;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.data.TodoSpecModel;
import cav.reminder.data.manager.DataManager;
import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.ui.adapters.TodoAdapter;
import cav.reminder.utils.ConstantManager;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private FloatingActionButton mFabNew;
    private TextView mName;

    private DataManager mDataManager;
    private TodoAdapter mTodoAdapter;

    private RecordHeaderRes mRecord;

    private int mode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mDataManager = DataManager.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabNew = (FloatingActionButton) findViewById(R.id.fab_todo_add_item);

        mFabNew.setOnClickListener(this);
        mName = (TextView) findViewById(R.id.short_text_tv);

        mode = getIntent().getIntExtra(ConstantManager.MODE_RECORD,-1);

        mListView = (ListView) findViewById(R.id.todo_list);

        ArrayList<TodoSpecModel> model = new ArrayList<>();
        mTodoAdapter = new TodoAdapter(this,R.layout.todo_item,model);
        mListView.setAdapter(mTodoAdapter);


        setupToolbar(toolbar);
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void saveDate(){
        if (mName.getText().toString().length() != 0) {
            ArrayList<TodoSpecModel> models = new ArrayList<>();
            for (int i=0;i<mTodoAdapter.getCount();i++){
                models.add(mTodoAdapter.getItem(i));
            }
            mRecord = new RecordHeaderRes(mName.getText().toString(),new Date(),"");
            mDataManager.getDataBaseConnector().addToDoRec(mRecord,models);
        }

    }
}
