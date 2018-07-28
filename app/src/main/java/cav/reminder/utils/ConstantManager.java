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
    String RECORD_PHOTO_FILE = "RECORD_PHOTO_FILE";

    int MODE_SEC_DIALOG_LOCK = 0;
    int MODE_SEC_DIALOG_UNLOCK = 1;


    int REQUEST_CAMERA_PICTURE = 99;

    int ITEM_TODO_NEW = 200;
    int ITEM_TODO_EDIT = 201;

    int TYPE_REC_MEMO  = 0;
    int TYPE_REC_TODO  = 1;

    String TODO_COUNT_SIZE = "TODO_COUNT_SIZE";
    String TODO_DONE_COUNT = "TODO_DONE_COUNT";

    int ALARM_CONST = 235;

    String TODO_POS_ID = "TODO_POS_ID";
    String TODO_REC_NAME = "TODO_REC_NAME";
    String TODO_REC_ID = "TODO_REC_ID";

    int NOTIFY_ID_F = 1050;
}
