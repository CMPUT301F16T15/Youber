package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.GeoLocation;
import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.collections.RequestCollection;

import java.util.ArrayList;

/**
 * The type Request view activity.
 */
public class RequestViewActivity extends AppCompatActivity {
    /**
     * The Request list view.
     */
    ListView requestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestListView = (ListView)findViewById(R.id.requestListView);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // if this line works with new stuff we
//        User user = UserController.getUser();
//        Log.i("New Firstname", user.getFirstName());

        // TODO CURRENTLY BORKEN BUT GONNA BE MOCKED OUT FOR NOW ^
        // so it should grab form the user model and then grab from the request models

    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO maybe in here we grab the list...?
        RequestCollection requests = new RequestCollection();

        GeoLocation start = new GeoLocation(-100, 100);
        GeoLocation end = new GeoLocation(-100, 50);
        Request request = new Request(start, end);
        requests.add(request);

        // this probably needs to be in request collection controller later as a function
        ArrayList<Request> requestArray = new ArrayList<Request>();
        requestArray.add(request);

        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(adapter);
    }

    private void requestDialog() {

    }
}
