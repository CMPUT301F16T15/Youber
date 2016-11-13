package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

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
        Intent intent = getIntent();
        RequestCollection requests=null;
        try{
            //Need this when Calvin is able to send a geolocation and double


            geoLocation=(GeoLocation) intent.getParcelableExtra("GeoLocation");
            if(geoLocation==null)throw new NullPointerException();
            radius=intent.getDoubleExtra("Radius",2.0);
            Log.i("radius: ", Double.toString(radius));
            //dummy stuff

            //geoLocation = new GeoLocation(53.507, -113.507);
            //radius = 200.0;

           requests = ElasticSearchController.getRequestsbyGeoLocation(geoLocation,radius);
        } catch (Exception e){
            String keyword=intent.getStringExtra("Keyword");
            requests=ElasticSearchController.getRequestsbyKeyWord(keyword);
        }


        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());

        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(adapter);
    }



}
