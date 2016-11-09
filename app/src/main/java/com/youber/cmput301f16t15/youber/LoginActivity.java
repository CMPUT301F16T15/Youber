package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{



    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginbutton);
        Button signUpButton = (Button) findViewById(R.id.signupbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                // Need to remove late, just used for testing.


                username = (EditText) findViewById(R.id.editText);

                ElasticSearchUser.getObjects searchUser = new ElasticSearchUser.getObjects();
                searchUser.execute(username.getText().toString());

               try {

                   ArrayList<User> users = searchUser.get();

                   if (users.size()!=1)
                   {
                       throw new Exception();
                   }

                   else
                   {
                       User user = users.get(0);
                       Log.i ("Works", "Found user"+user.getUsername());
                       Intent intent = new Intent(LoginActivity.this, UserTypeActivity.class);
                       startActivity(intent);
                       finish();
                   }
               }
               catch (Exception e)
               {
                   Log.i("Error", "The request for user has failed");
                   Bundle bundle = new Bundle();
                   bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.InvalidUsernameMessage));
                   bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.signup));
                   bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.ok));

                   DialogFragment dialog = new NoticeDialogFragment();
                   dialog.setArguments(bundle);
                   dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
               }


           }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
