package cav.reminder.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import cav.reminder.R;

public class EditDeleteDialog extends DialogFragment implements View.OnClickListener{

    private SelectEditDeleteListener mSelectEditDeleteListener;

    public interface SelectEditDeleteListener {
        void selectItem(int id);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_main_item, null);

        LinearLayout mEditLayout = (LinearLayout) v.findViewById(R.id.edit_laout);
        LinearLayout mDelLayout = (LinearLayout) v.findViewById(R.id.del_laout);

        mEditLayout.setOnClickListener(this);
        mDelLayout.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setView(v);
        return builder.create();
    }

    public void setSelectEditDeleteListener(SelectEditDeleteListener selectEditDeleteListener) {
        mSelectEditDeleteListener = selectEditDeleteListener;
    }

    @Override
    public void onClick(View v) {
        if (mSelectEditDeleteListener != null){
            mSelectEditDeleteListener.selectItem(v.getId());
        }
        dismiss();
    }
}