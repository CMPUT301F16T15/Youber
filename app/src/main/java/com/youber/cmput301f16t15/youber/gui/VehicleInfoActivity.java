package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Updater;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

public class VehicleInfoActivity extends AppCompatActivity {

    EditText make;
    EditText model;
    EditText year;
    EditText colour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        User user = UserController.getUser();

        make = (EditText) findViewById(R.id.editTextMake);
        make.setText(user.getMake());

        model = (EditText) findViewById(R.id.editTextModel);
        model.setText(user.getModel());

        year = (EditText) findViewById(R.id.editTextYear);
        year.setText(user.getYear());

        colour = (EditText) findViewById(R.id.editTextColour);
        colour.setText(user.getColour());

        Button saveVehicleInfo = (Button) findViewById(R.id.saveVehicleInfoButton);

        saveVehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makeText = make.getText().toString();
                String modelText = model.getText().toString();
                String yearText = year.getText().toString();
                String colourText = colour.getText().toString();

                UserController.setContext(VehicleInfoActivity.this);
                UserController.observable.addListener(new Updater());

                UserController.setVehicleMake(makeText);
                UserController.setVehicleModel(modelText);
                UserController.setVehicleYear(yearText);
                UserController.setVehicleColour(colourText);

                // Does not change in elastic search yet.
                Intent intent = new Intent(VehicleInfoActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
