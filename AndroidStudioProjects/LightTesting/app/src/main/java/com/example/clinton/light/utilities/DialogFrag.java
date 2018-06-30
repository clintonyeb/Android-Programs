package com.example.clinton.light.utilities;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.clinton.light.R;

public class DialogFrag extends DialogFragment {

    NoticeDialogListener mListener;
    RadioButton button_new;
    RadioButton button_relevant;
    DialogFacade mFacade;
    int position = 2;

    public DialogFrag() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mFacade = new DialogFacade();
        final View view = inflater.inflate(R.layout.dialog_design, null);

        builder.setView(view)
                .setTitle("Search For News")
                .setIcon(R.drawable.tool)

        .setPositiveButton(R.string.ok , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) view.findViewById(R.id.search_news);
                String word = editText.getText().toString();
                if (!word.isEmpty())
                {
                    mFacade.setSearchWord(word);
                    mFacade.setPosition(position);
                    mListener.onDialogPositiveClick(DialogFrag.this, mFacade);
                }
                else
                {
                    builder.setMessage("Enter keywords to search");
                }

            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(DialogFrag.this, null);
                        DialogFrag.this.getDialog().cancel();
                    }
                });
        button_new =  (RadioButton)view.findViewById(R.id.newest_button);
        button_relevant = (RadioButton)view.findViewById(R.id.relevant_button);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        button_relevant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        button_relevant.setChecked(true);

        return builder.create();
    }

    public interface NoticeDialogListener {
         void onDialogPositiveClick(DialogFragment dialog, DialogFacade facade);
         void onDialogNegativeClick(DialogFragment dialog, DialogFacade facade);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.newest_button:
                if (checked)
                    position = 1;
                    break;
            case R.id.relevant_button:
                if (checked)
                    position = 2;
                    break;
        }
    }


}
