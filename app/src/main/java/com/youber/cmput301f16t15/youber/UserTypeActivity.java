package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type User type activity.
 */
public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);


        Button riderOption = (Button) findViewById(R.id.riderButton);
        Button driverOption = (Button) findViewById(R.id.driverButton);


        driverOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                User user = UserController.getUser();
                UserController.setUserType(User.UserType.driver);

                Intent intent = new Intent(UserTypeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        riderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserController.getUser();
                UserController.setUserType(User.UserType.rider);

                Intent intent = new Intent(UserTypeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }





}
