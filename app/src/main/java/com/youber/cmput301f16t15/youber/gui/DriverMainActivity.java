package com.youber.cmput301f16t15.youber.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Setup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;

import java.io.IOException;
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
public class DriverMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MapView map;

    private int x, y;
    private static GeoPoint touchedPoint;
    private static GeoPoint searchPoint;

    private static Marker searchMarker;
    private static Polygon circle;

    private static double radius = 1;

    private static Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setup.run(this);

        setContentView(R.layout.activity_driver_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        //https://developer.android.com/guide/topics/ui/controls/spinner.html
        dropdown = (Spinner)findViewById(R.id.search_spinner);
        String[] items = new String[]{"Search", "Keyword", "Address"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

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


        Button searchByGeolocation = (Button) findViewById(R.id.search_geolocation_button);
        searchByGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchPoint == null) { // check for empty arguements
                    Snackbar.make(v, "Please select a point to search", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }

                GeoLocation geoLocation = new GeoLocation(searchPoint.getLatitude(), searchPoint.getLongitude());

                Intent intent = new Intent(DriverMainActivity.this,DriverSearchListActivity.class);
                intent.putExtra("GeoLocation", (Parcelable) geoLocation);
                intent.putExtra("Radius",radius);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                break;
            case 1:
                searchKeyword();
                break;
            case 2:
                searchAddress();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void searchKeyword(){
        String keyword = ((EditText)findViewById(R.id.keyword_search)).getText().toString();
        Intent intent = new Intent(DriverMainActivity.this, DriverSearchListActivity.class);
        intent.putExtra("Keyword",keyword);
        startActivity(intent);
    }

    public void searchAddress(){
        String address = ((EditText)findViewById(R.id.keyword_search)).getText().toString();
        if (address.isEmpty()){
            Toast.makeText(getBaseContext(), "Please enter an Address to search", Toast.LENGTH_LONG).show();
            dropdown.setSelection(0);
            return;
        }

        GeoLocation geoLocation = getFromLocation(address);
        String display = "lat: " + geoLocation.getLat() + "lon: " + geoLocation.getLon();
        Toast t = Toast.makeText(getBaseContext(), display, Toast.LENGTH_LONG);
        t.show();
        //Intent intent = new Intent(DriverMainActivity.this, DriverSearchListActivity.class);
        //intent.putExtra("Geolocation",geolocation);
        //finish();
        //startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        Setup.refresh(this);
        dropdown.setSelection(0);
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
            Intent intent = new Intent(this, DriverMainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_view_requests) {
            Intent intent = new Intent(this, RequestListActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_switch_user) {
            Intent intent = new Intent(this, UserTypeActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        AlertDialog.Builder searchRadiusDialog = new AlertDialog.Builder(DriverMainActivity.this);
                        LayoutInflater inflater = (LayoutInflater)DriverMainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
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

                            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
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
            }

            return true;
        }
    }

    //http://stackoverflow.com/questions/11932453/how-to-get-latitude-longitude-from-address-on-android by Kamal
    private GeoLocation getFromLocation(String address)
    {
        double latitude= 0.0, longtitude= 0.0;

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address , 1);
            if (addresses.size() > 0) {
                GeoPoint p = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6 ));

                latitude=p.getLatitude();
                longtitude=p.getLongitude();

                GeoLocation geoLocation = new GeoLocation(latitude, longtitude);

                return geoLocation;
            }
        }
        catch(Exception ee) {

        }

        GeoLocation geoLocation = new GeoLocation(latitude, longtitude);
        throw new RuntimeException();
    }
}
