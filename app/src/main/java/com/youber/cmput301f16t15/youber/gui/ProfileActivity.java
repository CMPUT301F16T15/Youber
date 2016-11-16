package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
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
public class ProfileActivity extends Activity {


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

        User user = UserController.getUser();
        username = (EditText) findViewById(R.id.contactUsername);
        username.setText(user.getUsername());

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
        // Author: Konstantin Burov

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
                UserController.observable.addListener(new ElasticSearchUser());

                UserController.setDateOfBirth(dateOfBirthText);
                UserController.setEmail(emailText);
                UserController.setFirstName(firstNameText);
                UserController.setLastName(lastNameText);

                // Does not change in elastic search yet.

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
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

}
