package cav.reminder.ui.activites;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import cav.reminder.R;
import cav.reminder.data.manager.DataManager;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private FloatingActionButton mFabNew;

    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mDataManager = DataManager.getInstance(this);

        mFabNew = (FloatingActionButton) findViewById(R.id.fab_todo_add_item);

        mFabNew.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.todo_list);

    }

    @Override
    public void onClick(View v) {

    }
}
