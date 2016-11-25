package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Updater;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

/**
 * The type Profile activity.
 *
 * <p>
 *     This activity is triggered when a user edits their profile
 * </p>
 *
 * @see LoginActivity
 * @see User
 */
public class ProfileActivity extends AppCompatActivity {


    EditText username;
    EditText email;
    EditText phoneNum;
    EditText dateOfBirth;
    EditText firstName;
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        User user = UserController.getUser();
        username = (EditText) findViewById(R.id.contactUsername);
        username.setText(user.getUsername());
        username.setKeyListener(null);
        username.setEnabled(false);

        email = (EditText) findViewById(R.id.contactEmail);
        email.setText(user.getEmail());

        phoneNum = (EditText) findViewById(R.id.contactPhone);
        phoneNum.setText(user.getPhoneNumber());

        dateOfBirth = (EditText) findViewById(R.id.contactDateOfBirth);
        dateOfBirth.setText(user.getDateOfBirth());

        firstName = (EditText) findViewById(R.id.contactFirstname);
        firstName.setText(user.getFirstName());

        lastName = (EditText) findViewById(R.id.contactLastName);
        lastName.setText(user.getLastName());


        Button saveInfo = (Button) findViewById(R.id.saveInfo);
        Button vehicleInfo = (Button) findViewById(R.id.editVehicleInfo);

        // Hide vehicle info button for riders
        //http://stackoverflow.com/questions/4127725/how-can-i-remove-a-button-or-make-it-invisible-in-android

        if(user.getCurrentUserType() == User.UserType.rider){
            vehicleInfo.setVisibility(View.GONE);
        }

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String phoneNumText = phoneNum.getText().toString();
                String dateOfBirthText = dateOfBirth.getText().toString();
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();

                UserController.setContext(ProfileActivity.this);
                UserController.observable.addListener(new Updater());

                UserController.setDateOfBirth(dateOfBirthText);
                UserController.setEmail(emailText);
                UserController.setFirstName(firstNameText);
                UserController.setLastName(lastNameText);
                UserController.setPhoneNumber(phoneNumText);

                // Does not change in elastic search yet.
                Intent intent = new Intent(ProfileActivity.this, RiderMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        vehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ProfileActivity.this, VehicleInfoActivity.class);
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
        else if (id == R.id.action_main) {
            Class c = (UserController.getUser().getCurrentUserType() == User.UserType.rider) ?
                    RiderMainActivity.class : DriverMainActivity.class;
            Intent intent = new Intent(this, c);
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


}
