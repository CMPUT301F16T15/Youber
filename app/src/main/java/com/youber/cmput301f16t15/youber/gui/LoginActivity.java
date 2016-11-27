package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.misc.Updater;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

/**
 * The type Login activity.
 * <p>
 *     This is the first activity shown when the app is opened. It contains two buttons and a textbox
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see SignUpActivity
 */
public class LoginActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Setup.run(this);

        Button loginButton = (Button) findViewById(R.id.loginbutton);
        Button signUpButton = (Button) findViewById(R.id.signupbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(!MacroCommand.isNetworkAvailable())
                    Toast.makeText(LoginActivity.this, getString(R.string.offline_login), Toast.LENGTH_SHORT).show();
                else {
                    EditText username = (EditText) findViewById(R.id.editText);
                    User user = ElasticSearchController.getUser(username.getText().toString());

                    if(user != null) { // able to get the unique user
//                        // TODO why do we add a command here? and listeners here
                        UserController.observable.addListener(new Updater());
                        AddUserCommand addUser = new AddUserCommand(user);
                        UserController.observable.notifyListeners(addUser);
                        UserController.saveUser(user);

                        Intent intent = new Intent(LoginActivity.this, UserTypeActivity.class);
                        startActivity(intent);
                    } else {
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

    // These positive click and negative click, are for when the user tries to log in but
    // is invalid: positive click is "Sign up", negative is "OK" does nothing
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
