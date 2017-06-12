package cav.reminder.utils;


/**
 * Created by cav on 02.08.16.
 */
public interface ConstantManager {
    String TAG_PREFIX = "REMINDER";
    String MODE_RECORD = "MODE_REOCRD";
    int MODE_INS_RECORD = 0;
    int MODE_EDIT_RECORD = 1;
    int MODE_VIEW_RECORD=2;

    String SHORT_DATA = "SHORT_DATA";
    String LONG_DATA = "LONG_DATA";
    String DATE_DATA = "DATE_DATA";

    int ITEM_ACTIVITY_NEW = 100;
    int ITEM_ACTIVITY_EDIT = 101;
    int ITEM_ACTIVITY_VIEW = 102;


    String RECORD_ID="RECORD_ID";
    String RECORD_HEADER="RECORD_HEADER";
    String RECORD_BODY="RECORD_BODY";
    String RECORD_CLOSE="RECORD_CLOSE";
    String RECORD_PASS_SAVE = "RECORD_PASS_SAVE";

    int MODE_SEC_DIALOG_LOCK = 0;
    int MODE_SEC_DIALOG_UNLOCK = 1;


    int REQUEST_CAMERA_PICTURE = 99;
}
