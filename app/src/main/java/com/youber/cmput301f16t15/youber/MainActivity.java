package com.youber.cmput301f16t15.youber;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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

/**
 * The type map activity.
 */
public class MainActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    //TODO use controller not global
    private Request request;
    private CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout = (CoordinatorLayout) findViewById(R.id.map_activity);

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
//
//                UserController.saveUser(user);
//                UserController.addListener(new ElasticSearchUser());
//                UserController.setFirstName("Scrum");
//
                ElasticSearchUser.add adder = new ElasticSearchUser.add();
                adder.execute(user);
//                ElasticSearchUser.getObjects getter = new ElasticSearchUser.getObjects();
//                getter.execute("Shade");
//                try {
//                    ArrayList<User> users = getter.get();
//                    int i = 0;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
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

        bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.dlg_create));
        bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.dlg_cancel));

        DialogFragment dialog = new NoticeDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) { // add new request
        ElasticSearchRequest.add addRequest = new ElasticSearchRequest.add();
        addRequest.execute(request);
        Snackbar.make(layout, "Successfully added a new request", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Snackbar.make(layout, "New request was not created", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
