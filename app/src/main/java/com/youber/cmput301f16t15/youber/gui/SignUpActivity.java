package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.misc.Updater;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;

/**
 * The type Sign up activity.
 *
 *
 * <p>
 *     This activity is triggered when a user signs up for the first time.
 * </p>
 *
 * @see LoginActivity
 * @see User
 */
public class SignUpActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    private EditText username;
    private EditText email;
    private EditText phoneNum;
    private EditText dateOfBirth;
    private EditText firstName;
    private EditText lastName;

    private TextView userString;
    private TextView phoneNumString;
    private TextView emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Setup.run(this);

        username = (EditText) findViewById(R.id.usernameInput);
        email = (EditText) findViewById(R.id.emailInput);
        phoneNum = (EditText) findViewById(R.id.phoneInput);
        dateOfBirth = (EditText) findViewById(R.id.dateofBirthInput);
        firstName = (EditText) findViewById(R.id.firstnameInput);
        lastName = (EditText) findViewById(R.id.lastNameInput);

        userString = (TextView) findViewById(R.id.textView2);
        phoneNumString = (TextView) findViewById(R.id.textView6);
        emailString = (TextView) findViewById(R.id.textView);

        Button createNewUser = (Button) findViewById(R.id.createNewUser);

        assert createNewUser != null;
        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MacroCommand.isNetworkAvailable()) {
                    Toast.makeText(SignUpActivity.this, "Currently offline: cannot create user", Toast.LENGTH_SHORT).show();
                    return;
                }

                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String phoneNumText = phoneNum.getText().toString();

                changeTextColour(usernameText, userString);
                changeTextColour(phoneNumText, phoneNumString);
                changeTextColour(emailText, emailString);

                if (TextUtils.isEmpty(emailText.trim()) || !emailText.contains("@")){
                    emailString.setTextColor(Color.RED);
                }
                
                signUpUser();
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

    // These clicks are for when the user already exits
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void signUpUser() {
        String usernameText = username.getText().toString();
        String emailText = email.getText().toString();
        String phoneNumText = phoneNum.getText().toString();
        String dateOfBirthText = dateOfBirth.getText().toString();
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();

        try
        {
            if(TextUtils.isEmpty(phoneNumText.trim()) ||
                    TextUtils.isEmpty(usernameText.trim()) ||
                    TextUtils.isEmpty(emailText.trim()) || !emailText.contains("@")) {
                Bundle bundle = new Bundle();
                bundle.putString("message", getString(R.string.validateFieldsMessage));
                bundle.putString(getString(R.string.positiveInput), "OK");

                DialogFragment dialog = new NoticeDialogFragment();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                return;
            }
            else{
                User esUser = ElasticSearchController.getUser(usernameText);

                if (esUser == null) { // the new username is unique and doesnt exist in Elastic search
                    User user = new User(usernameText, firstNameText, lastNameText, dateOfBirthText, phoneNumText, emailText);

                    UserController.observable.addListener(new Updater());
                    AddUserCommand addUser = new AddUserCommand(user);
                    UserController.observable.notifyListeners(addUser);
                    UserController.saveUser(user);

                    // add to commands to run and update to Elastic search
                    finish(); // finish since we shouldn't ever get back here

                    Intent intent = new Intent(SignUpActivity.this, UserTypeActivity.class);
                    startActivity(intent);
                }
                else { // this user already exists, can't create this user
                    Bundle bundle = new Bundle();
                    bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.usernameExitsMessage));
                    bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.ok));

                    DialogFragment dialog = new NoticeDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

