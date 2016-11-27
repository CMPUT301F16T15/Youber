package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.misc.Setup;
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
public class ProfileActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{


    private EditText email;
    private EditText phoneNum;
    private EditText dateOfBirth;
    private EditText firstName;
    private EditText lastName;

    private TextView phoneNumString;
    private TextView emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        User user = UserController.getUser();
        EditText username = (EditText) findViewById(R.id.contactUsername);
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

        phoneNumString = (TextView) findViewById(R.id.textView6);
        emailString = (TextView) findViewById(R.id.textView);



        Button saveInfo = (Button) findViewById(R.id.saveInfo);
        Button vehicleInfo = (Button) findViewById(R.id.editVehicleInfo);

        // Hide vehicle info button for riders
        //http://stackoverflow.com/questions/4127725/how-can-i-remove-a-button-or-make-it-invisible-in-android

        if(user.getCurrentUserType() == User.UserType.rider){
            assert vehicleInfo != null;
            vehicleInfo.setVisibility(View.GONE);
        }

        assert saveInfo != null;
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String phoneNumText = phoneNum.getText().toString();
                String dateOfBirthText = dateOfBirth.getText().toString();
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();

                changeTextColour(phoneNumText, phoneNumString);
                changeTextColour(emailText, emailString);

                if (!emailText.contains("@")){
                    emailString.setTextColor(Color.RED);
                }

                if(TextUtils.isEmpty(phoneNumText.trim()) || TextUtils.isEmpty(emailText.trim()) || !emailText.contains("@")){
                    Bundle bundle = new Bundle();
                    bundle.putString("message", getResources().getString(R.string.validateFieldsMessage));
                    bundle.putString(getResources().getString(R.string.positiveInput), "OK");

                    DialogFragment dialog = new NoticeDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
                else {
                    UserController.setContext(ProfileActivity.this);
                    UserController.observable.addListener(new Updater());

                    UserController.setDateOfBirth(dateOfBirthText);
                    UserController.setEmail(emailText);
                    UserController.setFirstName(firstNameText);
                    UserController.setLastName(lastNameText);
                    UserController.setPhoneNumber(phoneNumText);


                    Intent intent = new Intent(ProfileActivity.this, RiderMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        assert vehicleInfo != null;
        vehicleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ProfileActivity.this, VehicleInfoActivity.class);
                startActivity(intent);
                finish();
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
            Setup.refresh(this);
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
    }


    public void onDialogNegativeClick(DialogFragment dialog) { }
}
