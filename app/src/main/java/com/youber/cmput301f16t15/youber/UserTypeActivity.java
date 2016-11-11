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

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);


        Button riderOption = (Button) findViewById(R.id.riderButton);
        Button driverOption = (Button) findViewById(R.id.driverButton);

        user = UserController.getUser();
        driverOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserController.setUserType(User.UserType.driver);
                saveUserTypeRequests(user);

                Intent intent = new Intent(UserTypeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        riderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserController.setUserType(User.UserType.rider);
                saveUserTypeRequests(user);

                Intent intent = new Intent(UserTypeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }




    private void saveUserTypeRequests(User user)
    {
        RequestCollection requestCollection = ElasticSearchRequest.getRequestCollection(user.getRequestUUIDs());
        RequestCollectionsController.setContext(UserTypeActivity.this);
        RequestCollectionsController.saveRequestCollections(requestCollection);
    }



}
