package cav.reminder.ui.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class ItemActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "REMINDER_ITEM";

    private EditText mShort;
    private EditText mLong;
    private Button mSaveButton;

    private int mRecID=-1;
    private Date mDateRect;
    private int mode=0;
    private boolean mCloseRec = false;
    private String mKeyHash ;
    private File mPhotoFile = null;

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
            mCloseRec = getIntent().getBooleanExtra(ConstantManager.RECORD_CLOSE,false);
            mKeyHash = getIntent().getStringExtra(ConstantManager.RECORD_PASS_SAVE);
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"KEY HAS :"+mKeyHash);
        if (mCloseRec){
            // запись закрыта
            getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_UNLOCK,true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_item_activity,menu);
        if (mode==ConstantManager.MODE_VIEW_RECORD){
            Log.d(TAG,"GREATE MENU NO ENABLE");
            MenuItem item = menu.findItem(R.id.lock_rec);
            item.setEnabled(false);
            item = menu.findItem(R.id.unloc_rec);
            item.setEnabled(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG,"BACK BUTTON");
                onBackPressed();
                return true;
            case R.id.lock_rec:
                Log.d(TAG,"LOCK RECORD");
                getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_LOCK,false);
                break;
            case R.id.unloc_rec:
                getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_UNLOCK,false);
                break;
            case R.id.photo_rec:
                loadProtoFromCamera();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loadProtoFromCamera() {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            mPhotoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        startActivityForResult(captureImage,ConstantManager.REQUEST_CAMERA_PICTURE);

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFile = "JPEG_"+timeStamp+"_";
        // TODO переделать на свою директорию
      //  File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;

        File storageDir = new File (Environment.getExternalStorageDirectory(), "Reminder");
      // для общего каталога Pictures/
        //File path = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        if (! storageDir.exists()) {
            if (!storageDir.mkdirs()){
                return null;
            }
        }



        File image = File.createTempFile(imageFile,"jpg",storageDir);
        return image;
    }

    private String keyPass;

    /**
     * диалог получения секретного ключа
     */
    private void getSecyrityKeyDialog(final int mode,final boolean work_form){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Key");
        dialog.setContentView(R.layout.key_item_dialog);
        final EditText keyET = (EditText) dialog.findViewById(R.id.key_dialog_edit);
        Button okButton = (Button) dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"KEY DIALOG OK");
                keyPass= String.valueOf(keyET.getText());
                Log.d(TAG,keyPass);
                if (mode==ConstantManager.MODE_SEC_DIALOG_LOCK) {
                    mCloseRec = true;
                }
                if (mode == ConstantManager.MODE_SEC_DIALOG_UNLOCK) {
                    Log.d(TAG,Func.md5Hash(keyPass));
                    Log.d(TAG,mKeyHash);
                    if (Func.md5Hash(keyPass).equals(mKeyHash)){
                        Log.d(TAG,"PASS SUCCEFUL");
                    } else {
                        Log.d(TAG,"NO PASS");
                        return;
                    }
                    if (! work_form)
                        mCloseRec = false;
                }
                dialog.dismiss();
            }
        });
        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (work_form) {
                    finish();
                }
            }
        });
        dialog.show();

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
                answerIntent.putExtra(ConstantManager.RECORD_CLOSE,mCloseRec);
                answerIntent.putExtra(ConstantManager.RECORD_PASS_SAVE,Func.md5Hash(keyPass));
                answerIntent.putExtra(ConstantManager.RECORD_PHOTO_FILE,mPhotoFile.toString());
                if (mode==ConstantManager.MODE_EDIT_RECORD){
                    answerIntent.putExtra(ConstantManager.RECORD_ID,mRecID);
                }
                //answerIntent.putExtra(ConstantManager.DATE_DATA,); добавить текущую дату
                setResult(RESULT_OK, answerIntent);
                finish(); // закрываем активити
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile !=null){
                   // mSelectedImage = Uri.fromFile(mPhotoFile);
                  //  insertProfileImage(mSelectedImage);
                } else {
                    mPhotoFile = null;
                }
                break;
        }
    }
}
