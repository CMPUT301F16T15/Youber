package com.youber.cmput301f16t15.youber.gui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.requests.Request;

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
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *     This class is the main activity for a driver which will show once logged in as a driver.
 *     This functions similarly to the Rider class except they have Type specific options.
 * </p>
 *
 * @see com.youber.cmput301f16t15.youber.users.Rider
 * @see com.youber.cmput301f16t15.youber.users.User
 * @see MapQuestRoadManager
 * @see GeoPoint
 */
public class DriverMainActivity extends AppCompatActivity {

    //TODO use controller not global
    private Request request;
    private CoordinatorLayout layout;

    Activity ourActivity = this;
    MapView map;

    long start;
    long stop;
    int x, y;
    static GeoPoint touchedPoint;
    static GeoPoint searchPoint;

    static Marker searchMarker;
    static Polygon circle;

    static double radius = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        searchPoint = null;
        touchedPoint = null;

        IMapController mapController = map.getController();
        mapController.setZoom(12);
        //map currently focuses on Lister on launch
        GeoPoint EdmontonGPS = new GeoPoint(53.521609, -113.530633);
        mapController.setCenter(EdmontonGPS);

        Touch t = new Touch();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);

        Button searchByKeyword = (Button) findViewById(R.id.search_keyword_button);
        searchByKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String keyword = ((EditText)findViewById(R.id.keyword_search)).getText().toString();


                Intent intent = new Intent(DriverMainActivity.this, DriverSearchListActivity.class);
                intent.putExtra("Keyword",keyword);
                finish();
                startActivity(intent);

            }
        });

        Button searchByGeolocation = (Button) findViewById(R.id.search_geolocation_button);
        searchByGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchPoint == null) // check for empty arguements
                {
                    Snackbar.make(v, "Please select a point to search", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }

                GeoLocation geoLocation = new GeoLocation(searchPoint.getLatitude(), searchPoint.getLongitude());

                Intent intent = new Intent(DriverMainActivity.this,DriverSearchListActivity.class);
                intent.putExtra("GeoLocation", (Parcelable) geoLocation);
                intent.putExtra("Radius",radius);
                finish();
                startActivity(intent);


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

    public void clearMap(View view) {
        map.getOverlays().remove(searchMarker);
        map.getOverlays().remove(circle);
        searchPoint = null;
        map.invalidate();
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
                    //t.show();

                    if (searchPoint == null) {
                        searchPoint = new GeoPoint(touchedPoint.getLatitude(), touchedPoint.getLongitude());
                        String searchtLat = String.valueOf(searchPoint.getLatitude());
                        String searchLon = String.valueOf(searchPoint.getLongitude());
                        searchMarker = new Marker(map);
                        searchMarker.setPosition(searchPoint);
                        searchMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        searchMarker.setTitle("search point");
                        map.getOverlays().add(searchMarker);
                        map.invalidate();

                        //http://stackoverflow.com/questions/6424032/android-seekbar-in-dialog
                        AlertDialog.Builder searchRadiusDialog = new AlertDialog.Builder(ourActivity);
                        LayoutInflater inflater = (LayoutInflater)ourActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
                        final  View layout = inflater.inflate(R.layout.search_geolocation_dialog, (ViewGroup)findViewById(R.id.search_radius_dialog));
                        final TextView radiusText = (TextView)layout.findViewById(R.id.result);
                        radiusText.setText("1 meters");

                        searchRadiusDialog.setTitle("please set radius to search");
                        searchRadiusDialog.setView(layout);



                        SeekBar searchRadiusSeekbar = (SeekBar)layout.findViewById(R.id.search_radius_seekbar);
                        searchRadiusSeekbar.setMax(2000);
                        searchRadiusSeekbar.setProgress(1);
                        searchRadiusSeekbar.setKeyProgressIncrement(50);


                        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int result, boolean b) {
                                radius = result;
                                radiusText.setText(result + " meters");

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        };
                        searchRadiusSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);

                        searchRadiusDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //https://github.com/MKergall/osmbonuspack/wiki/Tutorial_5
                                circle = new Polygon();
                                circle.setPoints(Polygon.pointsAsCircle(searchPoint, radius));
                                circle.setFillColor(0x6984e1e1);
                                circle.setStrokeColor(Color.CYAN);
                                circle.setStrokeWidth(2);
                                map.getOverlays().add(circle);
                                map.invalidate();

                                dialogInterface.dismiss();
                            }
                        });

                        searchRadiusDialog.show();

                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {

            }

            return true;
        }
    }
}
