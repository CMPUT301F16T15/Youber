package com.youber.cmput301f16t15.youber;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

public class RequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    RelativeLayout layout;
    ListView driverListView;
    ArrayList<Driver> driverArray;
    int driverSelected;
    Request selectedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        layout = (RelativeLayout)findViewById(R.id.activity_request);
        driverListView = (ListView)findViewById(R.id.driverListView);

        driverListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Dialog dlg = promptDialog(R.layout.dlg_user_info); //test
                driverSelected = i;
                dlg.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        UUID selectedRequestUUID = (UUID)getIntent().getExtras().getSerializable("uuid");
        selectedRequest = RequestCollectionsController.getRequestCollection().getRequestByUUID(selectedRequestUUID);

        TextView status = (TextView)findViewById(R.id.status_info);
        status.setText(selectedRequest.getCurrentStatus().toString());

        TextView startLoc = (TextView)findViewById(R.id.start_loc_info);
        startLoc.setText(selectedRequest.getStartLocation().toString());

        TextView endLoc = (TextView)findViewById(R.id.end_loc_info);
        endLoc.setText(selectedRequest.getEndLocation().toString());
    }

    @Override
    protected void onResume() { // update the driver stuff
        super.onResume();

        driverArray = new ArrayList<Driver>();

        Driver driver1 = new Driver("driver1", "Jess", "Huynh", "10", "911", "@", User.UserType.driver);
        Driver driver2 = new Driver("driver2", "Caro", "Carlos", "10", "780", "@", User.UserType.driver);
        driverArray.add(driver1);
        driverArray.add(driver2);

        ArrayAdapter<Driver> adapter = new ArrayAdapter<Driver>(this, R.layout.list_item, driverArray);
        driverListView.setAdapter(adapter);
    }

    public void onOKBtnClick(View view) {
        finish();
    }

    public void onMoreOptionClick(View view) {
        Dialog dialog = promptDialog(R.layout.request_more_options);
        dialog.show();
    }

    public Dialog promptDialog(int resource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(resource, null))
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
        RequestCollectionsController.deleteRequest(selectedRequest);
        Snackbar.make(layout, "Successfully cancelled the request", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void onFairFareBtnClick(View view) {
//        selectedRequest.getDistance();
    }

    public void onViewRequestOnMapBtnClick(View view) {
    }

    public void onPhoneNumberClick(View view) {
        Driver driver = driverArray.get(driverSelected);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + driver.getPhoneNumber()));
        startActivity(intent);
    }

    public void onEmailClick(View view) {
    }
}
