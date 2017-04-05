package cav.reminder.ui.activites.activites.activites;

import android.os.Bundle;
import android.widget.ExpandableListView;

import cav.reminder.R;
import cav.reminder.ui.activites.activites.activites.BaseActivity;

public class MainActivity extends BaseActivity {

    ExpandableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ExpandableListView) findViewById(R.id.listView);

    }
}
