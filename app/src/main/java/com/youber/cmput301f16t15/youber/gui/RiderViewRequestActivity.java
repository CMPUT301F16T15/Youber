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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

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

/**
 * @see Request
 */
public class RiderViewRequestActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    RelativeLayout layout;
    ListView driverListView;
    ArrayList<User> driverArray = new ArrayList<User>();
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
        startLoc.setText(selectedRequest.getStartLocStr());

        TextView endLoc = (TextView)findViewById(R.id.end_loc_info);
        endLoc.setText(selectedRequest.getEndLocStr());

        TextView priceStr = (TextView)findViewById(R.id.price_value);
        Double price = RequestController.getPrice(selectedRequest);
        priceStr.setText(Double.toString(price));

        TextView distStr = (TextView)findViewById(R.id.distance_value);
        Double dist = RequestController.getDistanceOfRequest(selectedRequest);
        distStr.setText(Double.toString(dist) + getString(R.string.km));

        TextView userTitle = (TextView)findViewById(R.id.user_request_title);
        userTitle.setText((userType == User.UserType.driver)? "Rider":"Driver");

        TextView description = (TextView)findViewById(R.id.rider_request_descp);
        description.setText(selectedRequest.getDescription());
    }

    @Override
    protected void onResume() { // update the driver stuff
        super.onResume();
        Setup.refresh(this);

        TextView userTitle = (TextView)findViewById(R.id.user_request_title);
        if(selectedRequest.getCurrentStatus() == Request.RequestStatus.opened)
            userTitle.setText("No drivers have selected your request");
        else {
            if(!MacroCommand.isNetworkAvailable())
                userTitle.setText("Currently Offline cannot obtain drivers");
            else {
                if(selectedRequest.getCurrentStatus() == Request.RequestStatus.acceptedByDrivers)
                    driverArray = ElasticSearchController.getAcceptedDrivers(selectedRequest);
                else
                    driverArray = ElasticSearchController.getConfirmedDriver(selectedRequest);
            }
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, R.layout.list_item, driverArray);
        driverListView.setAdapter(adapter);
    }

    public void onOKBtnClick(View view) {
        finish();
    }

    public void onMoreOptionClick(View view) {

        Dialog dialog = promptDialog(R.layout.request_more_options);
        dialog.show();

        if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.opened)
                || selectedRequest.getCurrentStatus().equals(Request.RequestStatus.acceptedByDrivers)) {

                Button accept = (Button) dialog.findViewById(R.id.accept_request);
                accept.setVisibility(View.GONE);
                Button pay = (Button) dialog.findViewById(R.id.offer_payment);
                pay.setVisibility(View.GONE);
                Button accept_pay = (Button) dialog.findViewById(R.id.accept_payment);
                accept_pay.setVisibility(View.GONE);
        }
        else if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.riderSelectedDriver))
        {
            Button accept = (Button) dialog.findViewById(R.id.accept_request);
            accept.setVisibility(View.GONE);
            Button cancel = (Button) dialog.findViewById(R.id.cancel_request);
            cancel.setVisibility(View.GONE);
            Button accept_pay = (Button) dialog.findViewById(R.id.accept_payment);
            accept_pay.setVisibility(View.GONE);
        }
        else if (selectedRequest.getCurrentStatus().equals(Request.RequestStatus.paid)
            || selectedRequest.getCurrentStatus().equals(Request.RequestStatus.completed))
        {
            Button accept = (Button) dialog.findViewById(R.id.accept_request);
            accept.setVisibility(View.GONE);
            Button cancel = (Button) dialog.findViewById(R.id.cancel_request);
            cancel.setVisibility(View.GONE);
            Button pay = (Button) dialog.findViewById(R.id.offer_payment);
            pay.setVisibility(View.GONE);
            Button accept_pay = (Button) dialog.findViewById(R.id.accept_payment);
            accept_pay.setVisibility(View.GONE);
        }


    }

    public void onPayRequestBnClick(View view)
    {
        Dialog dialog = promptDialog(R.layout.dlg_payment);
        dialog.show();

        TextView payment = (TextView) dialog.findViewById(R.id.payment);
        payment.setText("Make payment of "+selectedRequest.getCost().toString()+" to driver?");
    }


    public Dialog promptDialog(int resource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        if (resource==R.layout.dlg_user_info && selectedRequest.getCurrentStatus().equals(Request.RequestStatus.acceptedByDrivers))
        {
            builder.setView(inflater.inflate(resource, null))
                    // Add action buttons
                    .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }}).setPositiveButton(R.string.dlg_accept, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // update the status and username for request
                            if(!MacroCommand.isNetworkAvailable())
                                Toast.makeText(RiderViewRequestActivity.this, "Currently offline: driver selection queued", Toast.LENGTH_SHORT).show();

                            selectedRequest.setRiderSelectedDriver();
                            selectedRequest.setDriverUsernameID(driverArray.get(driverSelected).getUsername());
                            RequestCollectionsController.addRequest(selectedRequest);

                            // add it to the confirmed list for driver
                            driverArray.get(driverSelected).addToDriverConfirmed(selectedRequest.getUUID());
                            AddUserCommand addUserCommand = new AddUserCommand(driverArray.get(driverSelected));
                            MacroCommand.addCommand(addUserCommand);
                            finish();
                }});
        }

        else if (resource==R.layout.dlg_payment) {
            builder.setView(inflater.inflate(resource, null))
                    .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            }).setPositiveButton(R.string.dlg_payment, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(!MacroCommand.isNetworkAvailable())
                                Toast.makeText(RiderViewRequestActivity.this, "Currently offline: payment queued", Toast.LENGTH_SHORT).show();

                            selectedRequest.setPaid();
                            RequestCollectionsController.addRequest(selectedRequest);
                            // dismiss dialog
                            dialog.dismiss();
                            finish();
                        }
                });
        }
        else{
            builder.setView(inflater.inflate(resource, null))
                    // Add action buttons
                    .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }


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

    @Override // deleting a request
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(!MacroCommand.isNetworkAvailable())
            Toast.makeText(this, "Currently Offline: delete request in queue", Toast.LENGTH_SHORT);

        RequestCollectionsController.deleteRequest(selectedRequest);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    public void onPhoneNumberClick(View view) {
        User driver = driverArray.get(driverSelected);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + driver.getPhoneNumber()));
        startActivity(intent);
    }

    public void onEmailClick(View view) {
        User driver = driverArray.get(driverSelected);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, driver.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.youber_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body));

        startActivity(Intent.createChooser(intent, "Send mail..."));
    }

    // MAP STUFF
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
        new UpdateRoadTask().execute(waypoints);
    }

    private class UpdateRoadTask extends AsyncTask<Object, Void, Road[]> {

        protected Road[] doInBackground(Object... params) {
            @SuppressWarnings("unchecked")
            ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];
            RoadManager roadManager = new OSRMRoadManager(RiderViewRequestActivity.this);
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
                String routeDesc = roads[i].getLengthDurationText(RiderViewRequestActivity.this, -1);
                roadPolyline.setTitle(getString(R.string.app_name) + " - " + routeDesc);
                roadPolyline.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, map));
                roadPolyline.setRelatedObject(i);
                mapOverlays.add(roadPolyline);
                map.invalidate();
            }
        }
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
            Intent intent = new Intent(this, RequestListActivity.class);
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
}
