package cav.reminder.ui.activites;


import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import cav.reminder.R;
import cav.reminder.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {
    private final String TAG = ConstantManager.TAG_PREFIX;

    protected ProgressDialog mProgressDialog;

    public void showProgress(){
/*
        if (mProgressDialog==null) {
            mProgressDialog = new ProgressDialog(this,R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }else{
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
*/
    }
    public void hideProgress(){
        if (mProgressDialog!=null){
            if (mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
        }
    }

    // показываем сообщение о ошибке
    public void showError(String message,Exception error){
        showToast(message);
        Log.e(TAG,String.valueOf(error));
    }

    // показываем высплывающее сообщение
    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
