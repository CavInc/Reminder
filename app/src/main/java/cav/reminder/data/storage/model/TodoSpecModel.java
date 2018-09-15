package cav.reminder.data;

import java.util.Date;

public class TodoSpecModel {
    private int mPosition;
    private String mName;
    private boolean mCheck;
    private Date mDate;
    private boolean mAlarm = false;

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
}