package cav.reminder.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import cav.reminder.data.storage.model.PhotoPictyreDataModel;
import cav.reminder.data.storage.model.TodoSpecModel;
import cav.reminder.services.AlarmTaskReciver;

/**
 * Created by Kotov Alexandr on 16.10.16.
 */
public class Func {
    public static Date strToDate(String dateS,String mask){
        if (dateS.equals(" ")) return null;
        DateFormat format = new SimpleDateFormat(mask);
        try {
            return format.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String dateToStr(Date dateS,String mask){
        DateFormat format = new SimpleDateFormat(mask);
        return format.format(dateS);
    }

    public static String generateUri(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;
        File path = new File (Environment.getExternalStorageDirectory(), "Reminder");
        if (! path.exists()) {
            if (!path.mkdirs()){
                return null;
            }
        }
        return path.getPath();
    }

    public static String md5Hash(String val){
        if (val==null) val="----------";
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(val.getBytes(),0,val.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    @SuppressWarnings({"deprecation"})
    public static Bitmap getPicSize(String mCurrentPhotoPath){
        int targetW = 600; //400 x 300
        int targetH = 400;

        // Читаем с inJustDecodeBounds=true для определения размеров
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
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }

    public static PhotoPictyreDataModel getPictyreSizeFile(String currentPhoto){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhoto, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        return new PhotoPictyreDataModel(photoW,photoH);
    }


    // добавим будильник
    public static void addAlert(Context context, Date date, TodoSpecModel model, int recid){
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmTaskReciver.class);
        intent.putExtra(ConstantManager.TODO_POS_ID,model.getPosition());
        intent.putExtra(ConstantManager.TODO_REC_NAME,model.getName());
        intent.putExtra(ConstantManager.TODO_REC_ID,recid);

        PendingIntent pi= PendingIntent.getBroadcast(context,ConstantManager.ALARM_CONST+recid+model.getPosition(),
                intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
    }

}
