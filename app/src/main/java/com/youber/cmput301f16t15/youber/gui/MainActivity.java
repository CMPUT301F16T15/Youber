package com.youber.cmput301f16t15.youber.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
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
 */
public class MainActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    //TODO use controller not global
    private Request request;

    Activity ourActivity = this;
    MapView map;
    Road[] mRoads;

    long start;
    long stop;
    int x, y;
    GeoPoint touchedPoint;
    ArrayList<OverlayItem> overlayItemArray = new ArrayList<>();
    GeoPoint startPoint;
    GeoPoint endPoint;
    LocationManager locationManager;
    String towers;
    int lat = 0;
    int lon = 0;


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
        GeoPoint EdmontonGPS = new GeoPoint(53.521609, -113.530633);
        mapController.setCenter(EdmontonGPS);

        Touch t = new Touch();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria crit = new Criteria();
//
//        towers = locationManager.getBestProvider(crit, false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        android.location.Location location = locationManager.getLastKnownLocation(towers);
//
//        if (location != null) {
//            lat = (int) location.getLatitude();
//            lon = (int) location.getLongitude();
//            GeoPoint ourLocation = new GeoPoint(lat, lon);
//            OverlayItem overlayItem = new OverlayItem("ourLocation", "this is our location", ourLocation );
//            //overlayList.add(overlayItem);
//        }else {
//            Toast.makeText(MainActivity.this, "couldnt get provider", Toast.LENGTH_SHORT).show();
//        }


        UserController.setContext(this);
        RequestCollectionsController.setContext(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        RequestCollectionsController.observable.addListener(new ElasticSearchRequest());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                GeoLocation geoLocation = new GeoLocation(53.507, -113.507);
//                Double radius = 200.0;
//                Intent intent = new Intent(MainActivity.this,DriverSearchListActivity.class);
//                intent.putExtra("GeoLocation", (Parcelable) geoLocation);
//                intent.putExtra("Radius",radius);
//                startActivity(intent);
//

                    //Intent intent = new Intent(MainActivity.this, DriverSearchListActivity.class);
                    //intent.putExtra("Keyword","Another");
                    //startActivity(intent);


//                User user = UserController.getUser();
//                RequestCollection requestCollection = ElasticSearchRequest.getRequestCollection(user.getRequestUUIDs());
//                RequestCollectionsController.saveRequestCollections(requestCollection);
//                //RequestCollection requestCollection1 = RequestCollectionsController.getRequestCollection();
//
//                GeoLocation g1 = new GeoLocation(startPoint.getLatitude(),startPoint.getLongitude());
//                GeoLocation g2 = new GeoLocation(endPoint.getLatitude(), endPoint.getLongitude());
//
//
//                Request request =new Request(g1,g2);
//                RequestCollectionsController.addRequest(request);


                int a=2+1;
//
//                ElasticSearchController.getAcceptedDrivers(request);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * On new request btn click.
     *
     * @param view the view
     */
//  Button Click Actions
    public void onNewRequestBtnClick(View view) {
        String startLatStr = ((EditText)findViewById(R.id.start_lat_edit)).getText().toString();
        String startLonStr = ((EditText)findViewById(R.id.start_lon_edit)).getText().toString();
        String endLatStr = ((EditText)findViewById(R.id.end_lat_edit)).getText().toString();
        String endLonStr = ((EditText)findViewById(R.id.end_lon_edit)).getText().toString();

        if(startLatStr.isEmpty() || startLonStr.isEmpty() || endLatStr.isEmpty() || endLonStr.isEmpty()) // check for empty arguements
        {
            Snackbar.make(view, "Cannot have empty values for latitudes and longitudes", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        Double startLat, startLon, endLat, endLon;
        try {
            startLat = Double.parseDouble(startLatStr);
            startLon = Double.parseDouble(startLonStr);
            endLat = Double.parseDouble(endLatStr);
            endLon = Double.parseDouble(endLonStr);
        }
        catch(NumberFormatException e) {
            Snackbar.make(view, "Invalid argument format. Please input doubles for latitudes and longitudes", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        GeoLocation start = new GeoLocation(startLat, startLon);
        GeoLocation end   = new GeoLocation(endLat, endLon);
        request = new Request(start, end);
        promptConfirmDialog(view);
    }


    /**
     * Prompt confirm dialog.
     *
     * @param view the view
     */
// Code that implements the dialog window that will ensure if the user wants to create the request or not
    public void promptConfirmDialog(final View view) {
        Bundle bundle = new Bundle();

        String start = request.getStartLocation().toString();
        String end = request.getEndLocation().toString();
        String msg = "Please confirm new request.\nStart: " + start + "\nEnd: " + end;
        bundle.putString(getResources().getString(R.string.message), msg);

        bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.next));
        bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.dlg_cancel));

        DialogFragment dialog = new NoticeDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) { // add new request
        Dialog dlg = promptToAddDescription();
        dlg.show();

        final EditText description = (EditText)dlg.findViewById(R.id.description_edit_text);
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String descStr = description.getText().toString();
                request.setDescription(descStr);
            }
        });
    }

    public Dialog promptToAddDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater(); // Get the layout inflater

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dlg_request_description, null))
                .setPositiveButton(R.string.dlg_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestCollectionsController.getRequestCollection();
                        RequestCollectionsController.addRequest(request);
                    }
                })
                // Add action buttons
                .setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public class Touch extends Overlay {

        @Override
        protected void draw(Canvas canvas, MapView mapView, boolean b) {

        }

        public boolean onTouchEvent(MotionEvent e, MapView m) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                start = e.getEventTime();
                x = (int) e.getX();
                y = (int) e.getY();
                touchedPoint = (GeoPoint) map.getProjection().fromPixels(x, y);
            }
            if (e.getAction() == MotionEvent.ACTION_UP) {
                stop = e.getEventTime();
            }
            if (stop - start > 1000) {
                Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
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
                            Marker startMarker = new Marker(map);
                            startMarker.setPosition(startPoint);
                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            startMarker.setTitle("start point");
                            map.getOverlays().add(startMarker);
                            map.invalidate();
                            Toast.makeText(getBaseContext(), "start location is set", Toast.LENGTH_LONG);
                        } else if (endPoint == null) {
                            endPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());
                            String endLat = String.valueOf(endPoint.getLatitude());
                            String endLon = String.valueOf(endPoint.getLongitude());
                            ((EditText)findViewById(R.id.end_lat_edit)).setText(endLat);
                            ((EditText)findViewById(R.id.end_lon_edit)).setText(endLon);
                            Marker endMarker = new Marker(map);
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
            }

            return false;
        }
    }

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
                Polyline roadPolyline = RoadManager.buildRoadOverlay(roads[i]);
                mRoadOverlays[i] = roadPolyline;
                String routeDesc = roads[i].getLengthDurationText(ourActivity, -1);
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
