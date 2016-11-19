package com.youber.cmput301f16t15.youber.gui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class DriverViewRequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{

    Button cancel;
    Request selectedRequest;
    User rider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView username = (TextView) findViewById(R.id.driverUsernameInput);

        UUID selectedRequestUUID = (UUID)getIntent().getExtras().getSerializable("uuid");
        selectedRequest = RequestCollectionsController.getRequest(selectedRequestUUID);
        rider = ElasticSearchController.getRider(selectedRequestUUID);


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setTextColor(getResources().getColor(R.color.lightGreen));
                Dialog dlg = promptDialog(R.layout.dlg_user_info); //test
                dlg.show();


                TextView title = (TextView)dlg.findViewById(R.id.usernameInfoTitle);
                title.setText(rider.getUsername());

                TextView email = (TextView)dlg.findViewById(R.id.emailLink);
                email.setText(rider.getEmail());

                TextView phone = (TextView) dlg.findViewById(R.id.phoneNumberLink);
                phone.setText(rider.getPhoneNumber());
            }
        });

        Button optionsButton = (Button) findViewById(R.id.options);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Dialog dialog = promptDialog(R.layout.request_more_options);

                    dialog.show();
                    Button cancel = (Button) dialog.findViewById(R.id.cancel_request);
                    cancel.setVisibility(View.GONE);
            }

        });



    }


    public void onAcceptRequestBnClick(View view)
    {
        Bundle bundle = new Bundle();

        bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.driverOfferRide));
        bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.yes));
        bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.no));


        DialogFragment dialog = new NoticeDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    public Dialog promptDialog(int resource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(resource, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view).setNegativeButton(R.string.dlg_cancel,
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
                    }
                });


        return builder.create();
    }



    @Override
    protected void onStart() {
        super.onStart();


        TextView status = (TextView) findViewById(R.id.driverViewStatusUpdate);
        status.setText(selectedRequest.getCurrentStatus().toString());
        if (rider != null)
        {
            TextView username = (TextView) findViewById(R.id.driverUsernameInput);
            username.setText(rider.getUsername());
            //implement the onclick button
        }
        TextView startLoc = (TextView) findViewById(R.id.driverViewStartLocInput);
        startLoc.setText(selectedRequest.getStartLocation().toString());

        TextView endLoc = (TextView) findViewById(R.id.driverViewEndLocInput);
        endLoc.setText(selectedRequest.getEndLocation().toString());

        TextView offeredPayment = (TextView) findViewById(R.id.driverViewOffPaymentInput);
        offeredPayment.setText(selectedRequest.getCost().toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_view_requests) {
            Intent intent = new Intent(this, RequestViewActivity.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_switch_user)
        {
            Intent intent = new Intent(this, UserTypeActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.logout)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // the way we update a user, update that request
        selectedRequest.setAcceptedByDrivers();
        RequestCollectionsController.addRequest(selectedRequest);
        finish();


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void onPhoneNumberClick(View view) {


        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + rider.getPhoneNumber()));
        startActivity(intent);
    }

    public void onEmailClick(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, rider.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.youber_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));

        startActivity(Intent.createChooser(intent, "Send mail..."));
    }
}
