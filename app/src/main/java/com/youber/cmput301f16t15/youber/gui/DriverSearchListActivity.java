package com.youber.cmput301f16t15.youber.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;

public class DriverSearchListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    ListView requestListView;
    ArrayList<Request> requestArray;
    GeoLocation geoLocation;
    Double radius;

    Spinner filter;
    static double min;
    static double max;
    static double pricePerKm;

    String keyword;

    static double maxPricePerKmd;


    boolean keywordOption=false;
    //boolean addressOption=false;
    boolean geoLocationOption=false;

    ArrayAdapter<Request> requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestListView = (ListView)findViewById(R.id.requestListView);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(DriverSearchListActivity.this, DriverViewRequestActivity.class);
                intent.putExtra("uuid", requestArray.get(i).getUUID());
                startActivity(intent);
            }
        });


        filter = (Spinner)findViewById(R.id.filter_spinner);
        String[] items = new String[]{"Filter", "Prices", "Price/Km"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        filter.setAdapter(stringArrayAdapter);
        filter.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        RequestCollection requests=null;
        try{
            geoLocation=(GeoLocation) intent.getParcelableExtra("GeoLocation");
            if(geoLocation==null)throw new NullPointerException();
            geoLocationOption=true;
            radius=intent.getDoubleExtra("Radius",2000.0);
            Log.i("radius: ", Double.toString(radius));
            Log.i("radius: ", Double.toString(geoLocation.getLat()));
            Log.i("radius: ", Double.toString(geoLocation.getLon()));
            requests = ElasticSearchController.getRequestsByGeoLocation(geoLocation,radius);
        } catch (Exception e){
            keyword=intent.getStringExtra("Keyword");
            try {
                requests = ElasticSearchController.getRequestsByKeyWord(keyword);
                keywordOption=true;
            } catch (Exception e1) {
                Log.i("Error", "Elastic search failed");
            }
        }

        User user = UserController.getUser();
        requests = RequestCollectionsController.hideUserRequestInSearch(user, requests);

        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());

        requestAdapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(requestAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        AlertDialog.Builder filterDialog = null;
        LayoutInflater inflater = null;


        switch (position) {
            case 0:
                break;
            case 1:
                filterDialog = new AlertDialog.Builder(DriverSearchListActivity.this);
                inflater = (LayoutInflater)DriverSearchListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dlg_request_filter_price, (ViewGroup)findViewById(R.id.filter_dialog));

                filterDialog.setTitle("Please set price filters");
                filterDialog.setView(layout);
                filterDialog.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText minText = (EditText)layout.findViewById(R.id.min_text);
                        if (!minText.getText().toString().isEmpty()){
                            min = Double.parseDouble(minText.getText().toString());
                        }
                        else {
                            min = Double.NaN;
                        }
                        EditText maxText = (EditText)layout.findViewById(R.id.max_text);
                        if (!maxText.getText().toString().isEmpty()) {
                            max = Double.parseDouble(maxText.getText().toString());
                        }
                        else {
                            max = Double.NaN;
                        }

                        String results = "min: " + min + "\n" +  "max: " + max + "\n";
                        Toast.makeText(getBaseContext(), results, Toast.LENGTH_LONG).show();
                        RequestCollection requestCollection= new RequestCollection();
                        if(keywordOption){
                            requestCollection = ElasticSearchController.getRequestsByKeywordFilteredByPrice(keyword, min, max);
                        }else if(geoLocationOption){
                            requestCollection = ElasticSearchController.getRequestsByGeoLocationFilteredByPrice(geoLocation, radius, min,max);
                        }
                        requestArray.clear();
                        requestArray.addAll(requestCollection.values());
                        requestAdapter.notifyDataSetChanged();
                        filter.setSelection(0);

                    }
                });
                filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_LONG).show();
                        filter.setSelection(0);

                    }
                });

                filterDialog.show();


                break;
            case 2:
                filterDialog = new AlertDialog.Builder(DriverSearchListActivity.this);
                inflater = (LayoutInflater)DriverSearchListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout2 = inflater.inflate(R.layout.dlg_request_filter_price_km, (ViewGroup)findViewById(R.id.filter_dialog));
                filterDialog.setTitle("Please set price by km filters");
                filterDialog.setView(layout2);
                filterDialog.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText minPricePerKm = (EditText)layout2.findViewById(R.id.price_per_text);
                        if (!minPricePerKm.getText().toString().isEmpty()) {
                            pricePerKm = Double.parseDouble(minPricePerKm.getText().toString());
                        }
                        else {
                            pricePerKm = Double.NaN;
                        }
                        EditText maxPricePerKm = (EditText)layout2.findViewById(R.id.max_price_per_text);
                        if (!maxPricePerKm.getText().toString().isEmpty()) {
                            maxPricePerKmd = Double.parseDouble(maxPricePerKm.getText().toString());
                        }
                        else {
                            maxPricePerKmd = Double.NaN;
                        }

                        String results = "minPricePerKm: " + pricePerKm
                                + "\n" + "maxPricePerKm: " + maxPricePerKmd;
                        Toast.makeText(getBaseContext(), results, Toast.LENGTH_LONG).show();
                        RequestCollection requestCollection= new RequestCollection();
                        if(keywordOption){
                            requestCollection = ElasticSearchController.getRequestsByKeywordFilteredByPricePerKm(keyword, pricePerKm, maxPricePerKmd);
                        }else if(geoLocationOption){
                            requestCollection = ElasticSearchController.getRequestsByGeoLocationFilteredByPricePerKm(geoLocation, radius, pricePerKm,maxPricePerKmd);
                        }
                        requestArray.clear();
                        requestArray.addAll(requestCollection.values());
                        requestAdapter.notifyDataSetChanged();
                        filter.setSelection(0);
                        

                    }});


                filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_LONG).show();
                        filter.setSelection(0);

                    }
                });

                filterDialog.show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
