package cav.reminder.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import cav.reminder.R;
import cav.reminder.utils.ConstantManager;

public class EditDeleteDialog extends DialogFragment implements View.OnClickListener{
    private static final String TYPE_ITEM = "TYPE_ITEM";
    private int mTypeItem;

    private SelectEditDeleteListener mSelectEditDeleteListener;

    public interface SelectEditDeleteListener {
        void selectItem(int id);

    }



    public EditDeleteDialog newInstance(int typeItem){
        Bundle args = new Bundle();
        args.putInt(TYPE_ITEM,typeItem);
        EditDeleteDialog dialog = new EditDeleteDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mTypeItem = getArguments().getInt(TYPE_ITEM);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_main_item, null);

        LinearLayout mEditLayout = (LinearLayout) v.findViewById(R.id.edit_laout);
        LinearLayout mDelLayout = (LinearLayout) v.findViewById(R.id.del_laout);
        if (mTypeItem == ConstantManager.TYPE_REC_MEMO) {
            v.findViewById(R.id.send_mail).setOnClickListener(this);
        } else {
            //v.findViewById(R.id.send_mail).setEnabled(false);
            //v.findViewById(R.id.send_mail).setBackgroundColor(getResources().getColor(R.color.app_gray));
            v.findViewById(R.id.send_mail).setVisibility(View.GONE);
            v.findViewById(R.id.send_mail_vb).setVisibility(View.GONE);
        }

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