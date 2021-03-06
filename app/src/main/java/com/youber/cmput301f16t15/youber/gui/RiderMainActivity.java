package com.youber.cmput301f16t15.youber.gui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The type map activity.
 * <p>
 *     This activity displays the requests on a map as a route.
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see org.osmdroid.bonuspack.routing.OSRMRoadManager
 */
public class RiderMainActivity extends AppCompatActivity {

    private Request request;

    private MapView map;
    private Double distance = 0.0;

    private Road[] mRoads;

    /**
     * Various map fields used to overlay the route on the map
     */
    private int x, y; // need to be global so we can clear
    private static GeoPoint startPoint;
    private static GeoPoint endPoint;
    private static Marker startMarker;
    private static Marker endMarker;
    private static Polyline roadPolyline;
    private static UpdateRoadTask async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_main);
        Setup.run(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(false);

        IMapController mapController = map.getController();
        mapController.setZoom(12);
        
        //map currently focuses on Lister on launch
        GeoPoint EdmontonGPS = new GeoPoint(53.521609, -113.530633);
        mapController.setCenter(EdmontonGPS);

        Touch t = new Touch();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);

        UserController.setContext(this);
        RequestCollectionsController.setContext(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        Setup.refresh(this);
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
        else if (id == R.id.action_main) {
            Setup.refresh(this);
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

    /**
     * initRequestObj This just initializes a request with the gui values in the activity
     *
     * @return Request a request is made with the gui values!
     */
    private Request initRequestObj() { // map and on new click both use this!
        if(startPoint == null || endPoint == null) {
            Toast.makeText(RiderMainActivity.this, "Please select a start and end point", Toast.LENGTH_SHORT).show();
            return null;
        }

        while(distance == 0.0); // asynchronous drawing of route to get distance, so wait

        GeoLocation start = new GeoLocation(startPoint.getLatitude(), startPoint.getLongitude());
        GeoLocation end   = new GeoLocation(endPoint.getLatitude(), endPoint.getLongitude());

        Geocoder geocoder = new Geocoder(this);
        String startStr = RequestController.getLocationStr(geocoder, start);
        String endStr = RequestController.getLocationStr(geocoder, end);

        return new Request(start, startStr, end, endStr);
    }

    /**
     * On new request btn click.
     *
     * @param view the map
     */
//  Button Click Actions
    public void onNewRequestBtnClick(View view) {
        request = initRequestObj();

        if(request != null) {
            RequestController.setRouteDistance(request, distance);
            promptToCreateRequest();
        }
    }

    /**
     * prompt the create request dialog, called on "New Request" button
     *
     */
    private void promptToCreateRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater(); // Get the layout inflater

        // Inflate and set the layout for the dialog, Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dlg_request_creation, null))
                .setPositiveButton(R.string.dlg_create, null) // null so we do logic to close it or not
                .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

        final Dialog dlg = builder.create();
        dlg.show();
        setCreateDialogFields(dlg);

        // http://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
        ((AlertDialog)dlg).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RequestController.isValidRequest(request)) {
                    RequestCollectionsController.getRequestCollection();
                    RequestCollectionsController.addRequest(request);
                    dlg.dismiss(); //Dismiss once everything is OK.

                    if(!MacroCommand.isNetworkAvailable())
                        Toast.makeText(RiderMainActivity.this, "Currently Offline: add request queued", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RiderMainActivity.this, "Successfully added request", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RiderMainActivity.this, "Invalid request. Please ensure all fields are valid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param dlg the create request setting, the text
     */
    private void setCreateDialogFields(Dialog dlg) {
        TextView startLoc = (TextView)dlg.findViewById(R.id.create_start_value);
        startLoc.setText(request.getStartLocStr());

        TextView endLoc = (TextView)dlg.findViewById(R.id.create_end_value);
        endLoc.setText(request.getEndLocStr());

        TextView dist = (TextView)dlg.findViewById(R.id.create_dist_value);
        Double distKM = RequestController.getDistanceOfRequest(request);
        dist.setText(Double.toString(distKM) + " km");

        TextView estFare = (TextView)dlg.findViewById(R.id.create_est_fare_value);
        Double estFareDb = RequestController.getEstimatedFare(request);
        String estFareStr = "$" + Double.toString(estFareDb);
        estFare.setText(estFareStr);

        final EditText payment = (EditText)dlg.findViewById(R.id.enter_payment_value);
        payment.setHint(estFareStr);
        payment.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String paymentStr = payment.getText().toString();
                try {
                    RequestController.setPaymentAmount(paymentStr, request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final EditText description = (EditText)dlg.findViewById(R.id.description_edit_text);
        description.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String descStr = description.getText().toString();
                request.setDescription(descStr);
            }
        });
    }

    // ------------------------ MAP FUNCTIONS ------------------------
    //youtube Android Application Development Tutorial series by thenewboston
    public class Touch extends Overlay {
        @Override
        protected void draw(Canvas canvas, MapView mapView, boolean b) {}

        //http://stackoverflow.com/questions/16665426/get-coordinates-by-clicking-on-map-openstreetmaps
        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView){
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            x = (int) e.getX();
            y = (int) e.getY();
            GeoPoint touchedPoint = (GeoPoint) map.getProjection().fromPixels(x, y);

            try {
                List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitude(), touchedPoint.getLongitude(), 1);
                if (address.size() > 0) {
                    String display = "";
                    for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++)
                        display += address.get(0).getAddressLine(i) + "\n";

                    display = display.trim();
                    Toast t = Toast.makeText(getBaseContext(), display, Toast.LENGTH_SHORT);
                    t.show();

                    if (startPoint == null) {
                        startPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());

                        startMarker = new Marker(map);
                        startMarker.setPosition(startPoint);
                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        startMarker.setTitle("start point");

                        map.getOverlays().add(startMarker);
                        map.invalidate();
                    } else if (endPoint == null) {
                        endPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());

                        endMarker = new Marker(map);
                        endMarker.setPosition(endPoint);
                        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        endMarker.setTitle("end point");

                        map.getOverlays().add(endMarker);
                        map.invalidate();
                    }
                    if (startPoint != null && endPoint != null) {
                        // http://stackoverflow.com/questions/38539637/osmbonuspack-roadmanager-networkonmainthreadexception
                        // accessed on October 27th, 2016
                        // author: yubaraj poudel
                        ArrayList<OverlayItem> overlayItemArray;
                        overlayItemArray = new ArrayList<>();

                        overlayItemArray.add(new OverlayItem("Starting Point", "This is the starting point", startPoint));
                        overlayItemArray.add(new OverlayItem("Destination", "This is the detination point", endPoint));
                        async = new UpdateRoadTask();
                        getRoadAsync(startPoint, endPoint);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }
    }

    //cmput301 lab8
    public void getRoadAsync(GeoPoint startPoint, GeoPoint destinationPoint) {
        mRoads = null;
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        waypoints.add(destinationPoint);
        async.execute(waypoints);
    }

    public class UpdateRoadTask extends AsyncTask<Object, Void, Road[]> {

        protected Road[] doInBackground(Object... params) {
            @SuppressWarnings("unchecked")
            ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];
            RoadManager roadManager = new MapQuestRoadManager("9EEnjA3zxWdtQSkkUxB7QKAo0jLpGaCb");
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
                roadPolyline = RoadManager.buildRoadOverlay(roads[i]);
                mRoadOverlays[i] = roadPolyline;
                String routeDesc = roads[i].getLengthDurationText(RiderMainActivity.this, -1);

                distance = roads[i].mLength;

                roadPolyline.setTitle(getString(R.string.app_name) + " - " + routeDesc);
                roadPolyline.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, map));
                roadPolyline.setRelatedObject(i);
                mapOverlays.add(roadPolyline);
                map.invalidate();
            }
        }
    }

    public void clearMap(View view) {
        map.getOverlays().remove(startMarker);
        map.getOverlays().remove(endMarker);

        startPoint = null;
        endPoint = null;

        map.getOverlays().remove(roadPolyline);
        map.invalidate();
    }
}
