package cav.reminder.ui.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.utils.ConstantManager;

public class ItemActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "REMINDER_ITEM";

    private EditText mShort;
    private EditText mLong;
    private Button mSaveButton;

    private int mRecID=-1;
    private Date mDateRect;
    private int mode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        mShort = (EditText) findViewById(R.id.short_et);
        mLong = (EditText) findViewById(R.id.long_et);
        mSaveButton = (Button) findViewById(R.id.save_item_button);

        mSaveButton.setOnClickListener(this);
        setupToolbar(toolbar);

        // получили переданное значение
        mode = getIntent().getIntExtra(ConstantManager.MODE_RECORD,-1);
        if ((mode==ConstantManager.MODE_EDIT_RECORD) || (mode==ConstantManager.MODE_VIEW_RECORD)) {
            mShort.setText(getIntent().getStringExtra(ConstantManager.RECORD_HEADER));
            mLong.setText(getIntent().getStringExtra(ConstantManager.RECORD_BODY));
            mRecID = getIntent().getIntExtra(ConstantManager.RECORD_ID,-1);
        }
        if (mode==ConstantManager.MODE_VIEW_RECORD){
            mShort.setFocusable(false);
            mShort.setLongClickable(false);
            mShort.setCursorVisible(false);
            mLong.setFocusable(false);
            mLong.setLongClickable(false);
            mLong.setCursorVisible(false);
            mSaveButton.setText(R.string.close_button_txt);
        }

    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Log.d(TAG,"BACK BUTTON");
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"BACK PRESSED");
        if (this.mode==ConstantManager.MODE_EDIT_RECORD){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.dialog_item_title));
            dialog.setMessage("Вы уверены что хотите выйти без сохранения ?");
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setPositiveButton(R.string.dialog_yes, myDialogClickListener);
            dialog.setNegativeButton(R.string.dialog_no, myDialogClickListener);
            //dialog.create();
            dialog.show();

            return;
        }
        super.onBackPressed();
    }

    DialogInterface.OnClickListener myDialogClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_item_button:
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date newDate = new Date();
                // сохраниили и отдали данные
                Intent answerIntent = new Intent();
                answerIntent.putExtra(ConstantManager.SHORT_DATA,mShort.getText().toString());
                answerIntent.putExtra(ConstantManager.LONG_DATA,mLong.getText().toString());
                answerIntent.putExtra(ConstantManager.DATE_DATA,format.format(newDate));
                if (mode==ConstantManager.MODE_EDIT_RECORD){
                    answerIntent.putExtra(ConstantManager.RECORD_ID,mRecID);
                }
                //answerIntent.putExtra(ConstantManager.DATE_DATA,); добавить текущую дату
                setResult(RESULT_OK, answerIntent);
                finish(); // закрываем активити
                break;
        }
    }
}
