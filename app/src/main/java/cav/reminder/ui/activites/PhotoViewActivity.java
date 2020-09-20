package cav.reminder.ui.activites;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import cav.reminder.R;
import cav.reminder.data.storage.model.PhotoPictyreDataModel;
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
            String file = getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
            PhotoPictyreDataModel photoData = Func.getPictyreSizeFile(file);
            if (!photoData.isLandscape()) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            mPhotoFile = new File(file);
            mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString()));
        }

    }
}
