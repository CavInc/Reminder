package cav.reminder.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cav.reminder.R;
import cav.reminder.utils.Func;

public class PhotoItemsAdapter extends RecyclerView.Adapter<PhotoItemsAdapter.ViewHolder>{
    private ArrayList<String> data;
    private OnClickImageListener mListener;

    public PhotoItemsAdapter(ArrayList<String> data,OnClickImageListener listener) {
        this.data = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = data.get(position);
        holder.mPhoto.setImageBitmap(Func.getPicSize(s.toString(),600,400));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public String getItem(int id){
        return data.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPhoto;
        private OnClickImageListener mListener;
        public ViewHolder(@NonNull View itemView,OnClickImageListener listener) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.photo_image_thr);
            mListener = listener;
            mPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onClickImageListener(getAdapterPosition(),data.get(getAdapterPosition()));
            }
        }
    }

    public interface OnClickImageListener {
        void onClickImageListener(int position,String photoFile);
    }

}
