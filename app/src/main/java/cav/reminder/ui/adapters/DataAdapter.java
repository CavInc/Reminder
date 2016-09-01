package cav.reminder.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import cav.reminder.R;
import cav.reminder.data.RecordHeaderRes;

public class DataAdapter extends ArrayAdapter<RecordHeaderRes> {

    private LayoutInflater mInflater;
    private int resLayout;
    private RecordHeaderRes[] objects;

    public DataAdapter(Context context, int resource, RecordHeaderRes[] objects) {
        super(context, resource, objects);
        //this.objects=objects;
        resLayout = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row=convertView;
        System.out.println(mInflater);
        if(row==null){
            row = mInflater.inflate(resLayout,parent,false);
            holder = new ViewHolder();
            holder.headerRec = (TextView) row.findViewById(R.id.recordTitle);
            holder.dataRec = (TextView) row.findViewById(R.id.dateTitle);
            holder.bodyRec = (TextView) row.findViewById(R.id.smallBodyRec);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();

        }

        RecordHeaderRes record = getItem(position);
        holder.headerRec.setText(record.getHeaderRec());
        holder.bodyRec.setText(record.getBodyRec());

        return row;
    }

    class ViewHolder {
        public TextView headerRec;
        public TextView dataRec;
        public TextView bodyRec;
    }

}
