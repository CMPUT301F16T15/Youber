package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
