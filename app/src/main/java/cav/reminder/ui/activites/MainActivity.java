package cav.reminder.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.data.RecordHeaderRes;
import cav.reminder.ui.adapters.DataAdapter;
import cav.reminder.utils.ConstantManager;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    ListView mListView;
    private ImageView newButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        newButton = (ImageView) findViewById(R.id.newButton);
        newButton.setOnClickListener(this);

        // отладочные данные
        RecordHeaderRes[] record = {
                new RecordHeaderRes("Яблоки",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"),
                new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2016"),"Таблетки от забывчивости"),
                new RecordHeaderRes("А вот прикольное фото",strToDate("23.08.2016"),"Фотка в парке "),
                new RecordHeaderRes("Яблоки",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"),
                new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2016"),"Таблетки от забывчивости"),
                new RecordHeaderRes("А вот прикольное фото",strToDate("23.08.2015"),"Фотка в парке "),
                new RecordHeaderRes("Яблоки fd",strToDate("12.05.2016"),"Нашол ялоки дешевые в 15 палатке на рынке"),
                new RecordHeaderRes("Не забыть выпить таблетки",strToDate("18.02.2015"),"Таблетки от забывчивости"),
                new RecordHeaderRes("А вот прикольное фото2",strToDate("23.08.2015"),"Фотка в парке ")
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newButton:
                showToast("Новая запись");
                Intent intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_INS_RECORD,true);
                startActivity(intent);
                break;
        }

    }
}
