package cav.reminder.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cav.reminder.R;

public class DateTimerFragment extends DialogFragment {
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    private OnDateTimeChangeListener mListener;

    public interface OnDateTimeChangeListener {
        public void OnDateTimeChange(Date date);
    }

    public void setOnDateTimeChangeListener (OnDateTimeChangeListener listener){
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.datetime_dialog, null);
        mDatePicker = (DatePicker) v.findViewById(R.id.dt_datepick);
        mTimePicker = (TimePicker) v.findViewById(R.id.dt_timepick);
        mTimePicker.setIs24HourView(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setMinute(c.get(Calendar.MINUTE));

        }else {
            mTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выбор даты и времен")
                .setView(v)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        int h;
                        int m;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            h = mTimePicker.getHour();
                            m = mTimePicker.getMinute();
                        }else {
                            h = mTimePicker.getCurrentHour();
                            m = mTimePicker.getCurrentMinute();
                        }
                        Date date = new GregorianCalendar(year, month, day,h,m).getTime();
                        if (mListener != null){
                            mListener.OnDateTimeChange(date);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_no,null);

        return builder.create();
    }
}