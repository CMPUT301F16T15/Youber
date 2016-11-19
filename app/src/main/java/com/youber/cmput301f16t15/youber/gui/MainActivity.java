package com.youber.cmput301f16t15.youber.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.utils.DouglasPeuckerReducer;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type map activity.
 * <p>
 *     This activity displays the requests on a map as a route.
 * </p>
 *
 * @see org.osmdroid.bonuspack.routing.OSRMRoadManager
 */
public class MainActivity extends AppCompatActivity {

    //TODO use controller not global
    private Request request;

    Activity ourActivity = this;
    MapView map;
    Road[] mRoads;

    Double distance = 0.0;

    /**
     * Various map fields used to overlay the route on the map
     */
    int x, y;
    GeoPoint touchedPoint;
    GeoPoint startPoint;
    GeoPoint endPoint;
    static Marker startMarker;
    static Marker endMarker;
    static Polyline roadPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


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

        return super.onOptionsItemSelected(item);
    }

    private Request initRequestObj(boolean throwInsteadOfToast) { // map and on new click both use this!
        String startLatStr = ((EditText)findViewById(R.id.start_lat_edit)).getText().toString();
        String startLonStr = ((EditText)findViewById(R.id.start_lon_edit)).getText().toString();
        String endLatStr = ((EditText)findViewById(R.id.end_lat_edit)).getText().toString();
        String endLonStr = ((EditText)findViewById(R.id.end_lon_edit)).getText().toString();

        if(startLatStr.isEmpty() || startLonStr.isEmpty() || endLatStr.isEmpty() || endLonStr.isEmpty()) // check for empty arguements
        {
            if(throwInsteadOfToast)
                throw new RuntimeException();
            else {
                Toast.makeText(MainActivity.this, "Cannot have empty values for latitudes and longitudes", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        Double startLat, startLon, endLat, endLon;
        try {
            startLat = Double.parseDouble(startLatStr);
            startLon = Double.parseDouble(startLonStr);
            endLat = Double.parseDouble(endLatStr);
            endLon = Double.parseDouble(endLonStr);
        }
        catch(NumberFormatException e) {
            if(throwInsteadOfToast)
                throw new RuntimeException();
            else {
                Toast.makeText(MainActivity.this, "Invalid argument format. Please input doubles for latitudes and longitudes", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        GeoLocation start = new GeoLocation(startLat, startLon);
        GeoLocation end   = new GeoLocation(endLat, endLon);
        return new Request(start, end);
    }

    public void clearMap(View view) {
        map.getOverlays().remove(startMarker);
        map.getOverlays().remove(endMarker);
        startPoint = null;
        endPoint = null;
        ((EditText)findViewById(R.id.start_lat_edit)).setText(null);
        ((EditText)findViewById(R.id.start_lon_edit)).setText(null);
        ((EditText)findViewById(R.id.end_lat_edit)).setText(null);
        ((EditText)findViewById(R.id.end_lon_edit)).setText(null);
        map.getOverlays().remove(roadPolyline);
        map.invalidate();
    }

    /**
     * On new request btn click.
     *
     * @param view the map
     */
//  Button Click Actions
    public void onNewRequestBtnClick(View view) {
        request = initRequestObj(false);
        RequestController.setRouteDistance(request, distance);

        if(request != null)
            promptToCreateRequest();
    }

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

        ((AlertDialog)dlg).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RequestController.isValidRequest(request)) {
                    RequestCollectionsController.getRequestCollection();
                    RequestCollectionsController.addRequest(request);
                    dlg.dismiss(); //Dismiss once everything is OK.
                    Toast.makeText(MainActivity.this, "Successfully added request", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Invalid request. Please ensure all fields are valid"
                            , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCreateDialogFields(Dialog dlg) {
        TextView startLoc = (TextView)dlg.findViewById(R.id.create_start_value);
        startLoc.setText(request.getStartLocation().toString());

        TextView endLoc = (TextView)dlg.findViewById(R.id.create_end_value);
        endLoc.setText(request.getEndLocation().toString());

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

    //youtube Android Application Development Tutorial series by thenewboston
    public class Touch extends Overlay {

        @Override
        protected void draw(Canvas canvas, MapView mapView, boolean b) {

        }

        //http://stackoverflow.com/questions/16665426/get-coordinates-by-clicking-on-map-openstreetmaps
        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView){
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            x = (int) e.getX();
            y = (int) e.getY();
            touchedPoint = (GeoPoint) map.getProjection().fromPixels(x, y);
            try {
                List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitude(), touchedPoint.getLongitude(), 1);
                if (address.size() > 0) {
                    String display = "Latitude: " + touchedPoint.getLatitude() + "\n" + "Longitude: " + touchedPoint.getLongitude() + "\n";
                    for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++) {
                        display += address.get(0).getAddressLine(i) + "\n";
                    }
                    Toast t = Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
                    t.show();

                    if (startPoint == null) {
                        startPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());
                        String startLat = String.valueOf(startPoint.getLatitude());
                        String startLon = String.valueOf(startPoint.getLongitude());
                        ((EditText)findViewById(R.id.start_lat_edit)).setText(startLat);
                        ((EditText)findViewById(R.id.start_lon_edit)).setText(startLon);
                        startMarker = new Marker(map);
                        startMarker.setPosition(startPoint);
                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        startMarker.setTitle("start point");
                        map.getOverlays().add(startMarker);
                        map.invalidate();
                        //************************************unused toast***********************************
                        Toast.makeText(getBaseContext(), "start location is set", Toast.LENGTH_LONG);
                    } else if (endPoint == null) {
                        endPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());
                        String endLat = String.valueOf(endPoint.getLatitude());
                        String endLon = String.valueOf(endPoint.getLongitude());
                        ((EditText)findViewById(R.id.end_lat_edit)).setText(endLat);
                        ((EditText)findViewById(R.id.end_lon_edit)).setText(endLon);
                        endMarker = new Marker(map);
                        endMarker.setPosition(endPoint);
                        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        endMarker.setTitle("end point");
                        map.getOverlays().add(endMarker);
                        map.invalidate();
                        Toast.makeText(getBaseContext(), "end location is set", Toast.LENGTH_LONG);
                    }
                    if (startPoint != null && endPoint != null) {
                        // http://stackoverflow.com/questions/38539637/osmbonuspack-roadmanager-networkonmainthreadexception
                        // accessed on October 27th, 2016
                        // author: yubaraj poudel
                        ArrayList<OverlayItem> overlayItemArray;
                        overlayItemArray = new ArrayList<>();

                        overlayItemArray.add(new OverlayItem("Starting Point", "This is the starting point", startPoint));
                        overlayItemArray.add(new OverlayItem("Destination", "This is the detination point", endPoint));
                        getRoadAsync(startPoint, endPoint);
                    }


                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {

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
        new UpdateRoadTask().execute(waypoints);
    }


    private class UpdateRoadTask extends AsyncTask<Object, Void, Road[]> {

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
                String routeDesc = roads[i].getLengthDurationText(ourActivity, -1);

                distance = roads[i].mLength;

                roadPolyline.setTitle(getString(R.string.app_name) + " - " + routeDesc);
                roadPolyline.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, map));
                roadPolyline.setRelatedObject(i);
                mapOverlays.add(roadPolyline);
                //selectRoad(0);
                map.invalidate();
                //we insert the road overlays at the "bottom", just above the MapEventsOverlay,
                //to avoid covering the other overlays.
            }
        }
    }
}
