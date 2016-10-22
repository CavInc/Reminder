package cav.reminder.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kotov Alexandr on 16.10.16.
 */
public class Func {
    public static Date strToDate(String dateS){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String dateToStr(Date dateS){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(dateS);
    }
}
