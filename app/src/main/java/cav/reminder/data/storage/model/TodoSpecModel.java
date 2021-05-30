package cav.reminder.data.storage.model;

import android.content.Intent;
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
    private boolean mDone = false; // сработавщий будильник

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

    public void setRecId(int recId) {
        mRecId = recId;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if (obj.getClass() == this.getClass()){
            if (getPosition() == ((TodoSpecModel) (obj)).getPosition()){
                return true;
            } else {
                return false;
            }
        }
        if (obj instanceof Integer) {
            if (this.getPosition() == (Integer) obj){
                return true;
            }
        }
        return false;
    }
}