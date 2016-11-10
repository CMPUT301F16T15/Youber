package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The type Sign up activity.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText phoneNum;
    EditText dateOfBirth;
    EditText firstName;
    EditText lastName;


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

                User user = new User(usernameText, firstNameText, lastNameText, dateOfBirthText, phoneNumText, emailText);
                UserController.setContext(SignUpActivity.this);
                UserController.saveUser(user);
                ElasticSearchUser.add adder = new ElasticSearchUser.add();
                adder.execute(user);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }
}
