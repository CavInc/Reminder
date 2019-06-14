package cav.reminder.ui.activites;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.reminder.R;
import cav.reminder.ui.dialogs.KeyDialog;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class ItemActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "REMINDER_ITEM";

    private EditText mShort;
    private EditText mLong;
    private Button mSaveButton;
    private ImageView mPhotoView;

    private int mRecID=-1;
    private Date mDateRect;
    private int mode=0;
    private boolean mCloseRec = false;
    private String mKeyHash ;
    private File mPhotoFile = null;
    private int keyMode=-1;
    private boolean work_form = false;
    private MenuItem itemUnlock;
    private MenuItem itemEdit;
    private MenuItem itemPhoto;
    private MenuItem itemLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        mShort = (EditText) findViewById(R.id.short_et);
        mLong = (EditText) findViewById(R.id.long_et);
        mSaveButton = (Button) findViewById(R.id.save_item_button);
        mPhotoView = (ImageView) findViewById(R.id.photo_item);
        mPhotoView.setOnClickListener(this);

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
            String sf = getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
            if (sf != null) {
                mPhotoFile = new File(getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE));
                mPhotoView.setVisibility(View.VISIBLE);
                //mPhotoView.setImageURI(Uri.fromFile(mPhotoFile));
                mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString()));
            }
        }
        if (mode == ConstantManager.MODE_VIEW_RECORD){
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
            mLong.setVisibility(View.INVISIBLE);
            keyMode = ConstantManager.MODE_SEC_DIALOG_UNLOCK;
            work_form = true;
            getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_UNLOCK,true);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_item_activity,menu);
        itemUnlock = menu.findItem(R.id.unloc_rec);
        itemEdit = menu.findItem(R.id.edit_rec);
        itemPhoto = menu.findItem(R.id.photo_rec);
        itemLock = menu.findItem(R.id.lock_rec);
        if (mode == ConstantManager.MODE_VIEW_RECORD){
            Log.d(TAG,"GREATE MENU NO ENABLE");
            itemLock.setEnabled(false);
            itemLock.setVisible(false);

            itemUnlock.setEnabled(false);
            itemUnlock.setVisible(false);

            itemPhoto.setVisible(false);
            itemEdit.setVisible(true);

        }
        if (!mCloseRec) {
            itemUnlock.setEnabled(false);
            itemUnlock.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.lock_rec:
                Log.d(TAG,"LOCK RECORD");
                keyMode = ConstantManager.MODE_SEC_DIALOG_LOCK;
                work_form = false;
                getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_LOCK,false);
                break;
            case R.id.unloc_rec:
                keyMode = ConstantManager.MODE_SEC_DIALOG_UNLOCK;
                work_form = false;
                getSecyrityKeyDialog(ConstantManager.MODE_SEC_DIALOG_UNLOCK,false);
                break;
            case R.id.photo_rec:
                loadProtoFromCamera();
                break;
            case R.id.edit_rec:
                selectEdit();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void selectEdit() {
        mode = ConstantManager.MODE_EDIT_RECORD;
        itemEdit.setVisible(false);
        itemPhoto.setVisible(true);
        itemPhoto.setEnabled(true);
        itemLock.setVisible(true);
        itemLock.setEnabled(true);

        mShort.setFocusable(true);
        mShort.setLongClickable(true);
        mShort.setCursorVisible(true);
        mShort.setEnabled(true);
        mShort.setFocusableInTouchMode(true);

        mLong.setFocusable(true);
        mLong.setLongClickable(true);
        mLong.setCursorVisible(true);
        mLong.setEnabled(true);
        mLong.setFocusableInTouchMode(true);

        mSaveButton.setText(R.string.save_button_txt);
    }

    private void loadProtoFromCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFile = "JPEG_"+timeStamp+"_";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return ;
        File storageDir = new File (Environment.getExternalStorageDirectory(), "Reminder");
        if (! storageDir.exists()) {
            if (!storageDir.mkdirs()){
                return;
            }
        }

        try {
            mPhotoFile = File.createTempFile(imageFile, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            Uri fileUri = FileProvider.getUriForFile(this,
                    this.getApplicationContext().getPackageName() + ".provider", mPhotoFile);

            captureImage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        } else {
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        }

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

        File image = File.createTempFile(imageFile, "jpg", storageDir);

        return image;
    }

    private String mKeyPass;

    /**
     * диалог получения секретного ключа
     */
    private void getSecyrityKeyDialog(final int mode,final boolean work_form){
        KeyDialog keyDialog = new KeyDialog();
        keyDialog.setKeyDialogListener(mKeyDialogListener);
        keyDialog.show(getSupportFragmentManager(),"KD");

        /*
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Key");
        dialog.setContentView(R.layout.key_item_dialog);
        final EditText keyET = (EditText) dialog.findViewById(R.id.key_dialog_edit);
        Button okButton = (Button) dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"KEY DIALOG OK");
                mKeyPass= String.valueOf(keyET.getText());
                Log.d(TAG,mKeyPass);
                if (mode==ConstantManager.MODE_SEC_DIALOG_LOCK) {
                    mCloseRec = true;
                }
                if (mode == ConstantManager.MODE_SEC_DIALOG_UNLOCK) {
                    Log.d(TAG,Func.md5Hash(mKeyPass));
                    Log.d(TAG,mKeyHash);
                    if (Func.md5Hash(mKeyPass).equals(mKeyHash)){
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
        */

    }

    KeyDialog.KeyDialogListener mKeyDialogListener = new KeyDialog.KeyDialogListener() {
        @Override
        public void okDialog(String keyPass) {
            Log.d(TAG,keyPass);
            mKeyPass = keyPass;
            if (mKeyPass.length() == 0 ) return;

            if (keyMode == ConstantManager.MODE_SEC_DIALOG_LOCK) {
                mCloseRec = true;
            }
            if (keyMode == ConstantManager.MODE_SEC_DIALOG_UNLOCK) {
                Log.d(TAG,Func.md5Hash(keyPass));
                Log.d(TAG,mKeyHash);
                if (Func.md5Hash(keyPass).equals(mKeyHash)){
                    Log.d(TAG,"PASS SUCCEFUL");
                    mLong.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG,"NO PASS");
                    finish();
                    return;
                }
                if (! work_form)
                    mCloseRec = false;
            }
        }

        @Override
        public void closeDialog() {
            if (work_form) {
                finish();
            }
        }
    };

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
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = new Date();
                // сохраниили и отдали данные
                Intent answerIntent = new Intent();
                answerIntent.putExtra(ConstantManager.SHORT_DATA,mShort.getText().toString());
                answerIntent.putExtra(ConstantManager.LONG_DATA,mLong.getText().toString());
                answerIntent.putExtra(ConstantManager.DATE_DATA,format.format(newDate));
                answerIntent.putExtra(ConstantManager.RECORD_CLOSE,mCloseRec);
                answerIntent.putExtra(ConstantManager.RECORD_PASS_SAVE,Func.md5Hash(mKeyPass));
                if (mPhotoFile != null) {
                    answerIntent.putExtra(ConstantManager.RECORD_PHOTO_FILE, mPhotoFile.toString());
                }
                if (mode==ConstantManager.MODE_EDIT_RECORD){
                    answerIntent.putExtra(ConstantManager.RECORD_ID,mRecID);
                }
                //answerIntent.putExtra(ConstantManager.DATE_DATA,); добавить текущую дату
                setResult(RESULT_OK, answerIntent);
                finish(); // закрываем активити
                break;
            case R.id.photo_item:
                Intent intent = new Intent(this,PhotoViewActivity.class);
                intent.putExtra(ConstantManager.RECORD_PHOTO_FILE,
                        getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE));
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile !=null){
                    Log.d(TAG,mPhotoFile.getAbsolutePath());
                   // mSelectedImage = Uri.fromFile(mPhotoFile);
                  //  insertProfileImage(mSelectedImage);
                    mPhotoView.setVisibility(View.VISIBLE);
                    mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString()));
                } else {
                    mPhotoFile = null;
                }
                break;
        }
    }

    // обрезка изображения
    private void setPic(String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = mPhotoView.getWidth();
        int targetH = mPhotoView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mPhotoView.setImageBitmap(bitmap);
    }


}
