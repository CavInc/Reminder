package cav.reminder.data;

import java.util.Date;

public class RecordHeaderRes {
    private int mId;
    private String mHeaderRec;
    private Date mDate;
    private String mBodyRec;

    public RecordHeaderRes(String headerRec, Date date, String bodyRec) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }

    public RecordHeaderRes(int id, String headerRec, Date date, String bodyRec) {
        mId = id;
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }

    public int getId(){
        return mId;
    }
    public String getHeaderRec() {
        return mHeaderRec;
    }

    public Date getDate() {
        return mDate;
    }

    public String getBodyRec() {
        return mBodyRec;
    }
}
