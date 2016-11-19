package com.youber.cmput301f16t15.youber.gui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @see Request
 */
public class RequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    RelativeLayout layout;
    ListView driverListView;
    ArrayList<Driver> driverArray = new ArrayList<Driver>();
    int driverSelected;
    Request selectedRequest;
    User.UserType userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        layout = (RelativeLayout)findViewById(R.id.activity_request);
        driverListView = (ListView)findViewById(R.id.requestUsersListView);

        driverListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Dialog dlg = promptDialog(R.layout.dlg_user_info); //test
                dlg.show();

                driverSelected = i;

                TextView title = (TextView)dlg.findViewById(R.id.usernameInfoTitle);
                title.setText(driverArray.get(i).getUsername());

                TextView email = (TextView)dlg.findViewById(R.id.emailLink);
                email.setText(driverArray.get(i).getEmail());

                TextView phone = (TextView) dlg.findViewById(R.id.phoneNumberLink);
                phone.setText(driverArray.get(i).getPhoneNumber());


                userType =(UserController.getUser().getCurrentUserType());
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

        TextView priceStr = (TextView)findViewById(R.id.price_value);
        Double price = RequestController.getPrice(selectedRequest);
        priceStr.setText(Double.toString(price));

        TextView distStr = (TextView)findViewById(R.id.distance_value);
        Double dist = RequestController.getDistanceOfRequest(selectedRequest);
        distStr.setText(Double.toString(dist) + getString(R.string.km));

        TextView userTitle = (TextView)findViewById(R.id.user_request_title);
        userTitle.setText((userType == User.UserType.driver)? "Rider":"Driver");
    }

    @Override
    protected void onResume() { // update the driver stuff
        super.onResume();

        try {
            driverArray = ElasticSearchController.getAcceptedDrivers(selectedRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Driver driver1 = new Driver("driver1", "Jess", "Huynh", "10", "7801234567", "test@gmail.com", User.UserType.driver);
        driverArray.add(driver1);
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
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
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
        Driver driver = driverArray.get(driverSelected);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, driver.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.youber_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));

        startActivity(Intent.createChooser(intent, "Send mail..."));
    }
}
