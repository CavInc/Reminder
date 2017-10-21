package cav.reminder.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import cav.reminder.R;
import cav.reminder.data.storage.model.RecordHeaderRes;
import cav.reminder.utils.ConstantManager;
import cav.reminder.utils.Func;

public class DataAdapter extends ArrayAdapter<RecordHeaderRes> {

    private LayoutInflater mInflater;
    private int resLayout;
    private RecordHeaderRes[] objects;

    public DataAdapter(Context context, int resource, ArrayList<RecordHeaderRes> objects) {
        super(context, resource, objects);
        //this.objects=objects;
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
            holder.headerRec = (TextView) row.findViewById(R.id.recordTitle);
            holder.dataRec = (TextView) row.findViewById(R.id.dateTitle);
            holder.bodyRec = (TextView) row.findViewById(R.id.smallBodyRec);
            holder.closeRec = (ImageView) row.findViewById(R.id.item_closerec_img);
            holder.photoFile = (ImageView) row.findViewById(R.id.item_photo_img);
            holder.todoItem = (ImageView) row.findViewById(R.id.item_todo);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }

        RecordHeaderRes record = getItem(position);
        holder.headerRec.setText(record.getHeaderRec());
        holder.dataRec.setText(Func.dateToStr(record.getDate()));

        if (record.getTypeRec() == ConstantManager.TYPE_REC_MEMO) {
            holder.todoItem.setVisibility(View.GONE);
            holder.closeRec.setVisibility(View.VISIBLE);
            holder.photoFile.setVisibility(View.VISIBLE);

            if (record.isCloseRec()) {
                holder.closeRec.setImageResource(R.drawable.ic_lock_black_24dp);
                holder.bodyRec.setVisibility(View.GONE);
            } else {
                holder.closeRec.setImageResource(R.drawable.ic_lock_unlock_24dp1);
                holder.bodyRec.setVisibility(View.VISIBLE);
            }
            if (record.getPhotoFile() != null && record.getPhotoFile().length() != 0) {
                holder.photoFile.setImageResource(R.drawable.ic_camera_alt_black_24dp);
            } else {
                holder.photoFile.setImageResource(R.drawable.ic_camera_alt_gray_24dp);
            }
        /*
        holder.bodyRec.setText(record.getBodyRec().substring(0,
                (record.getBodyRec().length() < 60 ? record.getBodyRec().length() : 60)));*/
            holder.bodyRec.setText(record.getBodyRec());
        }
        if (record.getTypeRec() == ConstantManager.TYPE_REC_TODO) {
            holder.todoItem.setVisibility(View.VISIBLE);
            holder.closeRec.setVisibility(View.GONE);
            holder.photoFile.setVisibility(View.GONE);
            holder.bodyRec.setVisibility(View.GONE);
        }

        return row;
    }



    class ViewHolder {
        public TextView headerRec;
        public TextView dataRec;
        public TextView bodyRec;
        public ImageView photoFile;
        public ImageView closeRec;
        public ImageView todoItem;
    }

}
