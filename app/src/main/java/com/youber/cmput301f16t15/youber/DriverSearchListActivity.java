package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DriverSearchListActivity extends AppCompatActivity {


    ListView requestListView;
    ArrayList<Request> requestArray;
    GeoLocation geoLocation;
    Double radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Need this when Calvin is able to send a geolocation and double

        Intent intent = getIntent();
        geoLocation=(GeoLocation) intent.getParcelableExtra("GeoLocation");
        radius=intent.getDoubleExtra("Radius",10.0);

        //dummy stuff

//        geoLocation = new GeoLocation(53.507, -113.507);
//        radius = 200.0;

        requestListView = (ListView)findViewById(R.id.requestListView);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        RequestCollection requests =ElasticSearchController.getRequestsbyGeoLocation(geoLocation,radius);

        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());

        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(adapter);
    }



}
