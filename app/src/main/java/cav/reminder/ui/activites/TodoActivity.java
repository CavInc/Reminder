package cav.reminder.ui.activites;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cav.reminder.R;
import cav.reminder.data.manager.DataManager;
import cav.reminder.utils.ConstantManager;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private FloatingActionButton mFabNew;
    private TextView mName;

    private DataManager mDataManager;

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

    @Override
    public void onClick(View v) {

    }
}
