package com.youber.cmput301f16t15.youber.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.youber.cmput301f16t15.youber.R;

public class VehicleInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);
        Button saveVehicleInfo = (Button) findViewById(R.id.saveVehicleInfoButton);
    }
}
