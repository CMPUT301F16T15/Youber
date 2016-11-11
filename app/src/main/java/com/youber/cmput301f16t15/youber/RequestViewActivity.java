package com.youber.cmput301f16t15.youber;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The type Request view activity.
 */
public class RequestViewActivity extends AppCompatActivity {
    /**
     * The Request list view.
     */
    ListView requestListView;
    ArrayList<Request> requestArray;

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
                Intent intent = new Intent(RequestViewActivity.this, RequestActivity.class);
                intent.putExtra("uuid", requestArray.get(i).getUUID());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // grab the requests!
        RequestCollection requests = RequestCollectionsController.getRequestCollection();
        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());

        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray);
        requestListView.setAdapter(adapter);
    }
}
