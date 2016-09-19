package cav.reminder.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String TAG ="REMINDER_MAIN";

    ListView mListView;
    private ImageView newButton;

    private DataAdapter mAdapter;

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

        mAdapter = new DataAdapter(this,R.layout.main_item_list,record);
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newButton:
                showToast("Новая запись");
                Intent intent = new Intent(MainActivity.this,ItemActivity.class);
                intent.putExtra(ConstantManager.MODE_INS_RECORD,true);
               // startActivity(intent);
                startActivityForResult(intent,ConstantManager.ITEM_ACTIVITY_NEW);
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
                    Log.d(TAG,data.getStringExtra(ConstantManager.SHORT_DATA));
                    System.out.println(data.getStringExtra(ConstantManager.LONG_DATA));
                    Log.d(TAG,data.getStringExtra(ConstantManager.DATE_DATA));
                    /*
                    RecordHeaderRes lrecord = new RecordHeaderRes(data.getStringExtra(ConstantManager.SHORT_DATA),
                            strToDate(data.getStringExtra(ConstantManager.DATE_DATA)),
                            data.getStringExtra(ConstantManager.LONG_DATA));
                    */
                    RecordHeaderRes lrecord= new RecordHeaderRes("А вот прикольное фото48",strToDate("23.08.2015"),"Фотка в парке ");
                    System.out.println(lrecord.getHeaderRec()+" :: "+lrecord.getDate());
                    mAdapter.add(lrecord);
                   // mAdapter.notifyDataSetChanged();
                }
                break;
        }
       // super.onActivityResult(requestCode, resultCode, data);
    }
}
