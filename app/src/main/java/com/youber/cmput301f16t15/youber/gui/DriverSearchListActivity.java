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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.util.ArrayList;

public class DriverSearchListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    ListView requestListView;
    ArrayList<Request> requestArray;
    GeoLocation geoLocation;
    Double radius;

    Spinner filter;


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
        String[] items = new String[]{"Filter", "Prices"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        filter.setAdapter(adapter);
        filter.setOnItemSelectedListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        RequestCollection requests=null;
        try{
            geoLocation=(GeoLocation) intent.getParcelableExtra("GeoLocation");
            if(geoLocation==null)throw new NullPointerException();
            radius=intent.getDoubleExtra("Radius",2.0);
            Log.i("radius: ", Double.toString(radius));
            Log.i("radius: ", Double.toString(geoLocation.getLat()));
            Log.i("radius: ", Double.toString(geoLocation.getLon()));
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

        switch (position) {
            case 0:
                break;
            case 1:
                AlertDialog.Builder filterDialog = new AlertDialog.Builder(DriverSearchListActivity.this);
                LayoutInflater inflater = (LayoutInflater)DriverSearchListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final  View layout = inflater.inflate(R.layout.dlg_request_filter, (ViewGroup)findViewById(R.id.filter_dialog));

                filterDialog.setTitle("please set price filters");
                filterDialog.setView(layout);
                filterDialog.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "Filtered", Toast.LENGTH_LONG).show();
                    }
                });
                filterDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_LONG).show();

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
