package cav.reminder.data.manager;

public class DataManager {
    private static DataManager INSTANCE = null;

    public DataManager() {
    }

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

}
