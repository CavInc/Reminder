package cav.reminder.data;

public class TodoSpecModel {
    private int mPosition;
    private String mName;
    private boolean mCheck;

    public TodoSpecModel(int position, String name, boolean check) {
        mPosition = position;
        mName = name;
        mCheck = check;
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
}