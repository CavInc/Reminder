package cav.reminder.ui.activites;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ScaleGestureDetector;



import com.ortiz.touchview.TouchImageView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import cav.reminder.R;
import cav.reminder.data.storage.model.PhotoPictyreDataModel;

import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;


public class PhotoViewActivity extends AppCompatActivity {
    private TouchImageView mPhotoView;
    private File mPhotoFile;

    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        mPhotoView =  findViewById(R.id.photo_view_iv);


        String sf = getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
        if (sf != null) {
            String file = getIntent().getStringExtra(ConstantManager.RECORD_PHOTO_FILE);
            PhotoPictyreDataModel photoData = Func.getPictyreSizeFile(file);
            if (!photoData.isLandscape()) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int w = size.x;
            int h = size.y;


            mPhotoFile = new File(file);
           // mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString(),w,h));
            //mPhotoView.setBitmap(Func.getPicSize(mPhotoFile.toString(),w,h));

            mPhotoView.setImageBitmap(Func.getPicSize(mPhotoFile.toString(),w,h));

        }

    }
}
