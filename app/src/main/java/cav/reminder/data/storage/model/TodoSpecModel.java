package cav.reminder.data;

import android.util.Log;

import java.util.Date;

import cav.reminder.utils.Func;

public class TodoSpecModel {
    private int mPosition;
    private String mName;
    private boolean mCheck;
    private Date mDate;
    private boolean mAlarm = false;
    private int mRecId;

    public TodoSpecModel(int position, String name, boolean check) {
        mPosition = position;
        mName = name;
        mCheck = check;
    }

    public TodoSpecModel(int position, String name, boolean check, Date date) {
        mPosition = position;
        mName = name;
        mCheck = check;
        mDate = date;
        if (date == null) return;
        Date x = new Date();
        if (mDate.after(x)){
            mAlarm = true;
            Log.d("TSM", Func.dateToStr(date,"yyyy-MM-dd HH:mm"));
            Log.d("TSM",Func.dateToStr(x,"yyyy-MM-dd HH:mm"));
        } else {
            Log.d("TSM","After :"+Func.dateToStr(date,"yyyy-MM-dd HH:mm"));
            Log.d("TSM","After :"+Func.dateToStr(x,"yyyy-MM-dd HH:mm"));
        }
        Log.d("TSM","VS A:"+mDate.after(x)); // после
        Log.d("TSM","VX B:"+mDate.before(x)); // перед

    }

    public int getRecId() {
        return mRecId;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getName() {
        return mName;
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setCheck(boolean check) {
        mCheck = check;
    }

    public boolean isAlarm() {
        return mAlarm;
    }

    public void setAlarm(boolean alarm) {
        mAlarm = alarm;
    }
}