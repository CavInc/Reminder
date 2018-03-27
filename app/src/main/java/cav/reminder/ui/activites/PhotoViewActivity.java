package cav.reminder.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import cav.reminder.R;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class PhotoViewActivity extends AppCompatActivity {
    private ImageView mPhotoView;
    private File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        mPhotoView = (ImageView) findViewById(R.id.photo_view_iv);

        String sf = getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
        if (sf != null) {
            mPhotoFile = new File(getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE));
            mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString()));
        }

    }
}
