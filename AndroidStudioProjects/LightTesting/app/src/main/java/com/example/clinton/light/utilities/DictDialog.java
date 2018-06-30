package com.example.clinton.light.utilities;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.clinton.light.R;

public class DictDialog extends DialogFragment {

    DictDialogListener mListener;

    public DictDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_2_design, null))
                .setTitle("Search The Dictonary")
                .setIcon(R.drawable.people)

                .setPositiveButton(R.string.ok , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDictDialogPositiveClick(DictDialog.this);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDictDialogNegativeClick(DictDialog.this);
                        DictDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    public interface DictDialogListener {
        public void onDictDialogPositiveClick(DialogFragment dialog);
        public void onDictDialogNegativeClick(DialogFragment dialog);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DictDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
