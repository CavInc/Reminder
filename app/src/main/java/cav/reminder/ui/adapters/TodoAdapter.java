package cav.reminder.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import java.util.List;

import cav.reminder.R;
import cav.reminder.data.TodoSpecModel;

public class TodoAdapter extends ArrayAdapter<TodoSpecModel>{
    private LayoutInflater mInflater;
    private int resLayout;

    public TodoAdapter(Context context, int resource, List<TodoSpecModel> objects) {
        super(context, resource, objects);
        resLayout = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row=convertView;
        if(row==null){
            row = mInflater.inflate(resLayout,parent,false);
            holder = new ViewHolder();
            holder.mTodoTextView = (CheckedTextView) row.findViewById(R.id.todo_item_ch);
            holder.mIcon = (ImageView) row.findViewById(R.id.todo_item_img);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        TodoSpecModel record = getItem(position);
        holder.mTodoTextView.setText(record.getName());
        holder.mTodoTextView.setChecked(record.isCheck());
        if (record.isAlarm()) {
            holder.mIcon.setImageResource(R.drawable.ic_alarm_black_24dp);
        } else {
            holder.mIcon.setImageResource(R.drawable.ic_alarm_gray_24dp);
        }

        return row;
    }

    class ViewHolder {
        public CheckedTextView mTodoTextView;
        public ImageView mIcon;

    }
}