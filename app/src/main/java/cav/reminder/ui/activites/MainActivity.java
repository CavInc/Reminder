package cav.reminder.ui.activites;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.data.RecordHeaderRes;
import cav.reminder.ui.adapters.DataAdapter;

public class MainActivity extends BaseActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);

        // отладочные данные
        RecordHeaderRes[] record = {
                new RecordHeaderRes("Яблоки",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"),
                new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2016"),"Таблетки от забывчивости")

        };

        DataAdapter adapter = new DataAdapter(this,R.layout.main_item_list,record);
        mListView.setAdapter(adapter);


    }

    private Date strToDate(String dateS){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
