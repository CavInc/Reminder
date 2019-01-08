package cav.reminder.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cav.reminder.R;

public class KeyDialog extends DialogFragment implements View.OnClickListener {

    public interface KeyDialogListener {
        void okDialog(String keyPass);
        void closeDialog();

    }

    private KeyDialogListener mKeyDialogListener;
    private EditText keyEt;

    public static KeyDialog newInstance(KeyDialogListener keyDialogListener,int mode,boolean work_form){
        Bundle arg = new Bundle();
        arg.putFloat("MODE",mode);
        KeyDialog dialog = new KeyDialog();
        dialog.setArguments(arg);
        return dialog;
    }


    public void setKeyDialogListener(KeyDialogListener keyDialogListener) {
        mKeyDialogListener = keyDialogListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.key_item_dialog, null);

        keyEt = v.findViewById(R.id.key_dialog_edit);
        v.findViewById(R.id.ok_button).setOnClickListener(this);
        v.findViewById(R.id.cancel_button).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Key").setView(v);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            mKeyDialogListener.closeDialog();
        }
        if (v.getId() == R.id.ok_button) {
           mKeyDialogListener.okDialog(keyEt.getText().toString());
        }
        dismiss();
    }
}