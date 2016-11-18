package com.youber.cmput301f16t15.youber.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;

import java.util.UUID;

public class DriverViewRequestActivity extends AppCompatActivity {


    Request selectedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_request);



    }

    @Override
    protected void onStart() {
        super.onStart();

        UUID selectedRequestUUID = (UUID)getIntent().getExtras().getSerializable("uuid");
        selectedRequest = RequestCollectionsController.getRequestCollection().getRequestByUUID(selectedRequestUUID);



    }

    }
