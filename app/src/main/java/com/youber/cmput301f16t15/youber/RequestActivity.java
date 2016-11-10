package com.youber.cmput301f16t15.youber;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class RequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        layout = (RelativeLayout)findViewById(R.id.activity_request);
    }

    public void onOKBtnClick(View view) {
        finish();
    }

    public void onMoreOptionClick(View view) {
        Dialog dialog = promptMoreOptionsDialog();
        dialog.show();
    }

    public Dialog promptMoreOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.request_more_options, null))
                // Add action buttons
                .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    // these are part of the dialog of more options
    public void onCancelRequestBnClick(View view) {
        Bundle bundle = new Bundle();

        bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.promptCancelConfirm));

        bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.yes));
        bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.no));

        DialogFragment dialog = new NoticeDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) { // add new request
//        ElasticSearchRequest.add addRequest = new ElasticSearchRequest.add();
//        addRequest.execute(request);
        Snackbar.make(layout, "Successfully cancelled the request", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void onFairFareBtnClick(View view) {
    }

    public void onViewRequestOnMapBtnClick(View view) {
    }
}
