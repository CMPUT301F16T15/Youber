package com.youber.cmput301f16t15.youber;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserController.setContext(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                User user = new User("Shade","Aaron","Philips","feb21","780","@google", User.UserType.driver);

                Request r = new Request(new GeoLocation(-113, 50), new GeoLocation(-112, 50));
                user.add(r);
                if(user.getRequests() == null)
                    Log.i("Error", "null list");

                UserController.saveUser(user);
                UserController.addObserver(new ElasticSearchUser());
                UserController.setFirstName("Scrum Master");
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

        Request request = new Request(start, end);
        promptConfirmDialog(request, view);
    }

    public void promptConfirmDialog(final Request request, final View view) {
        final ArrayList<Boolean> confirmed = new ArrayList<Boolean>(); // kind of a hacky way to set a boolean

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        String start = request.getStartLocation().toString();
        String end = request.getEndLocation().toString();
        alertBuilder.setMessage("Please confirm new request.\nStart: " + start + "\nEnd: " + end);

        alertBuilder.setCancelable(true);
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
                addNewRequest(view, request);
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
                Snackbar.make(view, "New request action was cancelled", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        AlertDialog deleteAlert = alertBuilder.create();
        deleteAlert.show();
    }

    public void addNewRequest(View view, Request request) {
        ElasticSearchRequest.add addRequest = new ElasticSearchRequest.add();
        addRequest.execute(request);
        Snackbar.make(view, "Successfully added a new request", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
