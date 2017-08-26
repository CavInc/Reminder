package cav.reminder.data.storage.model;

import java.util.Date;

public class RecordHeaderRes {
    private int mId;
    private String mHeaderRec;
    private Date mDate;
    private String mBodyRec;
    private String mPhotoFile;
    private String mAudioRecFile;
    private boolean mCloseRec; // зашифрованная запись
    private String mPassHash;
    private int mTypeRec; // тип записи
    private int mAllTodoCount; // количество все записей TO DO в блоке
    private int mDoneCount; // количество выполненных записей в блоке

    public RecordHeaderRes(String headerRec, Date date, String bodyRec) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }
    public RecordHeaderRes(String headerRec, Date date, String bodyRec,String photofile,boolean closeRec,String passHash) {
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
        mPhotoFile = photofile;
        mCloseRec = closeRec;
        mPassHash = passHash;
    }

    public RecordHeaderRes(int id, String headerRec, Date date, String bodyRec) {
        mId = id;
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
    }

    public RecordHeaderRes(int id, String headerRec, Date date, String bodyRec,String photofile,boolean closeRec,String passHash) {
        mId = id;
        mHeaderRec = headerRec;
        mDate = date;
        mBodyRec = bodyRec;
        mPhotoFile = photofile;
        mCloseRec = closeRec;
        mPassHash = passHash;
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

    public String getPassHash() {
        return mPassHash;
    }
}
