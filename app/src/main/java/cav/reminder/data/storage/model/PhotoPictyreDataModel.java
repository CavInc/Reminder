package cav.reminder.data.storage.model;

/**
 * Created by cav on 19.09.20.
 */

public class PhotoPictyreDataModel {
    private int mW;
    private int mH;
    private boolean mLandscape = false;

    public PhotoPictyreDataModel(int w, int h) {
        mW = w;
        mH = h;
        if (w > h) {
            mLandscape = true;
        }
    }

    public int getW() {
        return mW;
    }

    public int getH() {
        return mH;
    }

    public boolean isLandscape() {
        return mLandscape;
    }
}
