package com.youber.cmput301f16t15.youber.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.youber.cmput301f16t15.youber.R;

/**
 * Created by Reem on 2016-11-09.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */
public class NoticeDialogFragment extends DialogFragment {

    /**
     * The interface Notice dialog listener.
     */
// need to create a bundle with the arguments in order to use. Keys are stored in string.xml file
    public interface NoticeDialogListener {
        /**
         * On dialog positive click.
         *
         * @param dialog the dialog
         */
        public void onDialogPositiveClick(DialogFragment dialog);

        /**
         * On dialog negative click.
         *
         * @param dialog the dialog
         */
        //***************************************redundant public***********************************
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    /**
     * The M listener.
     */
// Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(getResources().getString(R.string.message)))
                .setPositiveButton(getArguments().getString(getResources().getString(R.string.positiveInput)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(NoticeDialogFragment.this);
                    }
                })
                .setNegativeButton(getArguments().getString(getResources().getString(R.string.negativeInput)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NoticeDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
