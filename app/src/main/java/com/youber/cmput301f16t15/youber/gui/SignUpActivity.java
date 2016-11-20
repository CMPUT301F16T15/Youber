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

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

    EditText username;
    EditText email;
    EditText phoneNum;
    EditText dateOfBirth;
    EditText firstName;
    EditText lastName;

    TextView userString;
    TextView phoneNumString;
    TextView emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String usernameText = username.getText().toString();
            String emailText = email.getText().toString();
            String phoneNumText = phoneNum.getText().toString();
            String dateOfBirthText = dateOfBirth.getText().toString();
            String firstNameText = firstName.getText().toString();
            String lastNameText = lastName.getText().toString();

            if (TextUtils.isEmpty(phoneNumText.trim())) {
                phoneNumString.setTextColor(Color.RED);
            }
            else {
                phoneNumString.setTextColor(Color.LTGRAY);
            }

            if (TextUtils.isEmpty(usernameText.trim())){
                userString.setTextColor(Color.RED);
            }
            else {
                userString.setTextColor(Color.LTGRAY);
            }

            if (TextUtils.isEmpty(emailText.trim())){
                emailString.setTextColor(Color.RED);
            }
            else {
                emailString.setTextColor(Color.LTGRAY);
            }

            try
            {
                if(TextUtils.isEmpty(phoneNumText.trim()) || TextUtils.isEmpty(usernameText.trim()) || TextUtils.isEmpty(emailText.trim())){
                    Bundle bundle = new Bundle();
                    bundle.putString("message", getResources().getString(R.string.validateFieldsMessage));
                    bundle.putString(getResources().getString(R.string.positiveInput), "OK");

                    DialogFragment dialog = new NoticeDialogFragment();
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                    return;
                }
                else{
                    ElasticSearchUser.getObjects getter = new ElasticSearchUser.getObjects();
                    getter.execute(usernameText);
                    ArrayList<User> users = getter.get();

                    if (users.size()==0)
                    {

                        User user = new User(usernameText, firstNameText, lastNameText, dateOfBirthText, phoneNumText, emailText);

                        UserController.setContext(SignUpActivity.this);
                        UserController.saveUser(user);

                        AddUserCommand addUser = new AddUserCommand(user);
                        MacroCommand.addCommand(addUser);
                        finish();
                    }
                    else
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.usernameExitsMessage));
                        bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.ok));
                        bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.login));

                        DialogFragment dialog = new NoticeDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            }
        });
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finish();
    }

    public void changeEmptyFieldsText(){

    }
}
