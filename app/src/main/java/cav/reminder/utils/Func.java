package cav.reminder.utils;

import android.os.Environment;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kotov Alexandr on 16.10.16.
 */
public class Func {
    public static Date strToDate(String dateS,String mask){
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
}
