package cav.reminder.data;

import java.util.Date;

public class RecordHeaderRes {
    private int mId;
    private String mHeaderRec;
    private Date mDate;
    private String mBodyRec;
    private String mPhotoFile;
    private String mAudioRecFile;
    private boolean mCloseRec; // зашифрованная запись

    public RecordHeaderRes(String headerRec, Date date, String bodyRec) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }
    public RecordHeaderRes(String headerRec, Date date, String bodyRec,String photofile,boolean closeRec) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
        mPhotoFile = photofile;
        mCloseRec = closeRec;
    }

    public RecordHeaderRes(int id, String headerRec, Date date, String bodyRec) {
        mId = id;
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }

    public RecordHeaderRes(int id, String headerRec, Date date, String bodyRec,String photofile,boolean closeRec) {
        mId = id;
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
        mPhotoFile = photofile;
        mCloseRec = closeRec;
    }

    public int getId(){
        return mId;
    }
    public void setId(int id){
        mId=id;
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

    public String getPhotoFile() {
        return mPhotoFile;
    }

    public String getAudioRecFile() {
        return mAudioRecFile;
    }

    public boolean isCloseRec() {
        return mCloseRec;
    }
    public int getCloseRec(){
        if (mCloseRec) return 1;
        else return 0;
    }
}
