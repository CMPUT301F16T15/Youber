package com.youber.cmput301f16t15.youber.gui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class DriverViewRequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{

    Button cancel;
    Request selectedRequest;
    User rider;
    Dialog moreOptionsDialog;
    UUID selectedRequestUUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView username = (TextView) findViewById(R.id.driverUsernameInput);

        selectedRequestUUID = (UUID)getIntent().getExtras().getSerializable("uuid");
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

            moreOptionsDialog = promptDialog(R.layout.request_more_options);
            moreOptionsDialog.show();
            if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.opened)
                    || selectedRequest.getCurrentStatus().equals(Request.RequestStatus.acceptedByDrivers)) {
                Button cancel = (Button) moreOptionsDialog.findViewById(R.id.cancel_request);
                cancel.setVisibility(View.GONE);
                Button pay = (Button) moreOptionsDialog.findViewById(R.id.offer_payment);
                pay.setVisibility(View.GONE);
                Button accept_pay = (Button) moreOptionsDialog.findViewById(R.id.accept_payment);
                accept_pay.setVisibility(View.GONE);
            }
            else if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.riderSelectedDriver))
            {
                Button cancel = (Button) moreOptionsDialog.findViewById(R.id.cancel_request);
                cancel.setVisibility(View.GONE);
                Button pay = (Button) moreOptionsDialog.findViewById(R.id.offer_payment);
                pay.setVisibility(View.GONE);
                Button accept = (Button) moreOptionsDialog.findViewById(R.id.accept_request);
                accept.setVisibility(View.GONE);
                Button accept_pay = (Button) moreOptionsDialog.findViewById(R.id.accept_payment);
                accept_pay.setVisibility(View.GONE);
            }
            else if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.paid)) {
                Button cancel = (Button) moreOptionsDialog.findViewById(R.id.cancel_request);
                cancel.setVisibility(View.GONE);
                Button pay = (Button) moreOptionsDialog.findViewById(R.id.offer_payment);
                pay.setVisibility(View.GONE);
                Button accept = (Button) moreOptionsDialog.findViewById(R.id.accept_request);
                accept.setVisibility(View.GONE);
            }
            else if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.completed))
            {
                Button cancel = (Button) moreOptionsDialog.findViewById(R.id.cancel_request);
                cancel.setVisibility(View.GONE);
                Button pay = (Button) moreOptionsDialog.findViewById(R.id.offer_payment);
                pay.setVisibility(View.GONE);
                Button accept = (Button) moreOptionsDialog.findViewById(R.id.accept_request);
                accept.setVisibility(View.GONE);
                Button accept_pay = (Button) moreOptionsDialog.findViewById(R.id.accept_payment);
                accept_pay.setVisibility(View.GONE);
            }


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

        if (resource==R.layout.dlg_payment)
        {
            builder.setView(inflater.inflate(resource, null))
                    .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).setPositiveButton(R.string.dlg_payment, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    selectedRequest.setCompleted();
                    RequestCollectionsController.addRequest(selectedRequest);
                    dialog.dismiss();
                    moreOptionsDialog.dismiss();
                    loadRequest();
                }
            });

        }
        else {
            View view = inflater.inflate(resource, null);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view).setNegativeButton(R.string.dlg_cancel,
                    new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }

        return builder.create();
    }


    public void onAcceptPaymentBnClick(View view)
    {

        Dialog dialog = promptDialog(R.layout.dlg_payment);
        dialog.show();

        TextView payment = (TextView) dialog.findViewById(R.id.payment);
        payment.setText("Would you like to accept payment "+selectedRequest.getCost().toString()+" from rider "+rider.getUsername()+"?");
    }



    @Override
    protected void onStart() {
        super.onStart();

        loadRequest();

    }


    public void loadRequest()
    {
        TextView status = (TextView) findViewById(R.id.driverViewStatusUpdate);
        status.setText(selectedRequest.getCurrentStatus().toString());
        if (rider != null)
        {
            TextView username = (TextView) findViewById(R.id.driverUsernameInput);
            username.setText(rider.getUsername());
            //implement the onclick button
        }
        TextView startLoc = (TextView) findViewById(R.id.driverViewStartLocInput);
        startLoc.setText(selectedRequest.getStartLocStr());

        TextView endLoc = (TextView) findViewById(R.id.driverViewEndLocInput);
        endLoc.setText(selectedRequest.getEndLocStr());

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
        dialog.dismiss();
        moreOptionsDialog.dismiss();
        loadRequest();

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

    MapView map;
    public void onViewRequestOnMapBtnClick(View view) {
        Dialog dialog = promptDialog(R.layout.dlg_request_map);
        dialog.show();

        map = (MapView)dialog.findViewById(R.id.request_map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        GeoPoint startLoc = new GeoPoint(selectedRequest.getStartLocation().getLat(), selectedRequest.getStartLocation().getLon());
        GeoPoint endLoc = new GeoPoint(selectedRequest.getEndLocation().getLat(), selectedRequest.getEndLocation().getLon());

        IMapController mapController = map.getController();
        mapController.setZoom(12);
        mapController.setCenter(startLoc);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(startLoc);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle("Start point: " + selectedRequest.getStartLocStr());
        map.getOverlays().add(startMarker);

        Marker endMarker = new Marker(map);
        endMarker.setPosition(endLoc);
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        endMarker.setTitle("end point: " + selectedRequest.getEndLocStr());
        map.getOverlays().add(endMarker);

        // http://stackoverflow.com/questions/38539637/osmbonuspack-roadmanager-networkonmainthreadexception
        // Author: yubaraj poudel
        ArrayList<OverlayItem> overlayItemArray;
        overlayItemArray = new ArrayList<>();

        overlayItemArray.add(new OverlayItem("Start Location", "This is the start location", startLoc));
        overlayItemArray.add(new OverlayItem("End Location", "This is the end location", endLoc));
        getRoadAsync(startLoc, endLoc);
    }

    Road[] mRoads;
    public void getRoadAsync(GeoPoint startPoint, GeoPoint destinationPoint) {
        mRoads = null;

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>(2);
        waypoints.add(startPoint);
        waypoints.add(destinationPoint);
        new DriverViewRequestActivity.UpdateRoadTask().execute(waypoints);
    }

    private class UpdateRoadTask extends AsyncTask<Object, Void, Road[]> {
        protected Road[] doInBackground(Object... params) {
            @SuppressWarnings("unchecked")
            ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];
            RoadManager roadManager = new OSRMRoadManager(DriverViewRequestActivity.this);
            return roadManager.getRoads(waypoints);
        }

        @Override
        protected void onPostExecute(Road[] roads) {
            mRoads = roads;
            if (roads == null)
                return;
            if (roads[0].mStatus == Road.STATUS_TECHNICAL_ISSUE)
                Toast.makeText(map.getContext(), "Technical issue when getting the route", Toast.LENGTH_SHORT).show();
            else if (roads[0].mStatus > Road.STATUS_TECHNICAL_ISSUE) //functional issues
                Toast.makeText(map.getContext(), "No possible route here", Toast.LENGTH_SHORT).show();

            Polyline[] mRoadOverlays = new Polyline[roads.length];
            List<Overlay> mapOverlays = map.getOverlays();
            for (int i = 0; i < roads.length; i++) {
                Polyline roadPolyline = RoadManager.buildRoadOverlay(roads[i]);
                mRoadOverlays[i] = roadPolyline;
                String routeDesc = roads[i].getLengthDurationText(DriverViewRequestActivity.this, -1);
                roadPolyline.setTitle(getString(R.string.app_name) + " - " + routeDesc);
                roadPolyline.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, map));
                roadPolyline.setRelatedObject(i);
                mapOverlays.add(roadPolyline);
                map.invalidate();
            }
        }
    }
}
