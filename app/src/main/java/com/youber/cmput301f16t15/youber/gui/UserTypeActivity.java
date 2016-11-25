package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

/**
 * The type User type activity.
 * Used to select either the rider or the driver based on two buttons.
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
                choseUserType(User.UserType.driver);
            }
        });

        riderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseUserType(User.UserType.rider);
            }
        });
    }

    private void choseUserType(User.UserType userType) {
        UserController.setUserType(userType);
        saveUserTypeRequests(user);
        Intent intent = new Intent(UserTypeActivity.this, RiderMainActivity.class);
        startActivity(intent);
    }

    private void saveUserTypeRequests(User user)
    {
        Setup.run(this);
        Log.i("Requests:" ,Integer.toString(user.getRequestUUIDs().size()));
        RequestCollection requestCollection = ElasticSearchRequest.getRequestCollection(user.getRequestUUIDs());
        RequestCollectionsController.setContext(UserTypeActivity.this);
        RequestCollectionsController.saveRequestCollections(requestCollection);
    }
}
