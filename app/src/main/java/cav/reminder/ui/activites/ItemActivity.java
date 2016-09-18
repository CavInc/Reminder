package cav.reminder.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cav.reminder.R;
import cav.reminder.utils.ConstantManager;

public class ItemActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "REMINDER_ITEM";

    private EditText mShort;
    private EditText mLong;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mShort = (EditText) findViewById(R.id.short_et);
        mLong = (EditText) findViewById(R.id.long_et);
        mSaveButton = (Button) findViewById(R.id.save_item_button);

        mSaveButton.setOnClickListener(this);

        // получили переданное значение
        boolean mode = getIntent().getBooleanExtra(ConstantManager.MODE_INS_RECORD,true);
        Log.d(TAG,"MODE :"+mode);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_item_button:
                // сохраниили и отдали данные
                Intent answerIntent = new Intent();
                answerIntent.putExtra(ConstantManager.SHORT_DATA,mShort.getText().toString());
                answerIntent.putExtra(ConstantManager.LONG_DATA,mLong.getText().toString());
                //answerIntent.putExtra(ConstantManager.DATE_DATA,); добавить текущую дату
                setResult(RESULT_OK, answerIntent);
                finish(); // закрываем активити
                break;
        }
    }
}
