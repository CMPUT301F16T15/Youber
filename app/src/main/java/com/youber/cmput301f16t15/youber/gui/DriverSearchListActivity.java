package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;

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
                Intent intent = new Intent(DriverSearchListActivity.this, DriverViewRequestActivity.class);
                intent.putExtra("uuid", requestArray.get(i).getUUID());
                startActivity(intent);
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

           requests = ElasticSearchController.getRequestsbyGeoLocation(geoLocation,radius);
        } catch (Exception e){
            String keyword=intent.getStringExtra("Keyword");
            try {
                requests = ElasticSearchController.getRequestsbyKeyWord(keyword);
            } catch (Exception e1) {
                Log.i("Error", "Elastic search failed");
            }
        }


        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());

        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(adapter);
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





}
