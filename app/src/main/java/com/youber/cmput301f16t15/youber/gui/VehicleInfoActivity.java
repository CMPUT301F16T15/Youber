package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Updater;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;


/**
 *
 * This class handles inputting a vehicle description for a driver. It is only accessible
 * by a driver and is accessed through the profile activity
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see ProfileActivity
 * @see UserTypeActivity
 */
public class VehicleInfoActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    private EditText make;
    private EditText model;
    private EditText year;
    private EditText colour;

    private TextView makeString;
    private TextView modelString;
    private TextView yearString;
    private TextView colourString;

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

        makeString = (TextView) findViewById(R.id.vehicleMake);
        modelString = (TextView) findViewById(R.id.vehicleModel);
        yearString = (TextView) findViewById(R.id.vehicleYear);
        colourString = (TextView) findViewById(R.id.vehicleColour);

        Button saveVehicleInfo = (Button) findViewById(R.id.saveVehicleInfoButton);

        assert saveVehicleInfo != null;
        saveVehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makeText = make.getText().toString();
                String modelText = model.getText().toString();
                String yearText = year.getText().toString();
                String colourText = colour.getText().toString();

                changeTextColour(makeText, makeString);
                changeTextColour(modelText, modelString);
                changeTextColour(yearText, yearString);
                changeTextColour(colourText, colourString);

                if (TextUtils.isEmpty(makeText.trim()) || TextUtils.isEmpty(modelText.trim()) || TextUtils.isEmpty(yearText.trim()) || TextUtils.isEmpty(colourText.trim())){
                    Bundle bundle = new Bundle();
                    bundle.putString("message", getResources().getString(R.string.validateFieldsMessage));
                    bundle.putString(getResources().getString(R.string.positiveInput), "OK");

                    DialogFragment dialog = new NoticeDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
                else {
                    UserController.setContext(VehicleInfoActivity.this);
                    UserController.observable.addListener(new Updater());

                    UserController.setVehicleMake(makeText);
                    UserController.setVehicleModel(modelText);
                    UserController.setVehicleYear(yearText);
                    UserController.setVehicleColour(colourText);

                    Intent intent = new Intent(VehicleInfoActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }

    private void changeTextColour(String text, TextView textview){
        if (TextUtils.isEmpty(text.trim())){
            textview.setTextColor(Color.RED);
        }
        else {
            textview.setTextColor(Color.LTGRAY);
        }
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
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) { }

}
