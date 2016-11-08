package com.youber.cmput301f16t15.youber;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class RequestViewActivity extends AppCompatActivity {
    ListView requestList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Bundle bundle = getIntent().getExtras();
//        String username = (String) bundle.get("username");
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        User user = UserController.getUser();
        Log.i("New Firstname", user.getFirstName());
//        user = UserCollection.getUsers().get(username);
    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        Rider rider = (Rider)user;
//
//        ArrayList<Request> request = new ArrayList<Request>();
//        for(Request r : rider.getRequests().values())
//            request.add(r);
//
//        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, request);
//        requestList.setAdapter(adapter);
    }
}
