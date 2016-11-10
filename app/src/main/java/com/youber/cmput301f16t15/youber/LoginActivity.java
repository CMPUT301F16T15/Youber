package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Login activity.
 */
public class LoginActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{

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

               Bundle bundle = new Bundle();
               bundle.putString(getResources().getString(R.string.message), getResources().getString(R.string.InvalidUsernameMessage));
               bundle.putString(getResources().getString(R.string.positiveInput), getResources().getString(R.string.signup));
               bundle.putString(getResources().getString(R.string.negativeInput), getResources().getString(R.string.ok));

               DialogFragment dialog = new NoticeDialogFragment();
               dialog.setArguments(bundle);
               dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
           }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
