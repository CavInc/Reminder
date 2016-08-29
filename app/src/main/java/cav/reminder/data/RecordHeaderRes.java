package cav.reminder.data;

import java.util.Date;

public class RecordHeaderRes {
    private String mHeaderRec;
    private Date mDate;
    private String mBodyRec;

    public RecordHeaderRes(String headerRec, Date date, String bodyRec) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
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
