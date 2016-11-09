package com.youber.cmput301f16t15.youber;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
//    private Rider user = new Rider("Shade","Aaron","Philips","feb21","780","@google");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserController.setContext(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                ElasticSearchUser.add userAdder = new ElasticSearchUser.add();
                final User user = new User("Shade","Aaron","Philips","feb21","780","@google");
                UserController.saveUser(user);
                UserController.addObserver(new ElasticSearchUser());
                UserController.setFirstName("Guy in front of us");

//                userAdder.execute(user);
//                UserCollection.getUsers().put(user.getUsername(), user);
//                User user = new User("Shade", "Aaron", "Phillips", "")
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_view_requests) {
            Intent intent = new Intent(this, RequestViewActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  Button Click Actions
    public void onNewRequestBtnClick(View view) {
        // prompt user for start location
        // prompt user for end location
        // confirm dialog

        GeoLocation start = new GeoLocation(-113, 50);
        GeoLocation end   = new GeoLocation(-113, 100);

        Request request = new Request(start, end);

        ElasticSearchRequest.add addRequest = new ElasticSearchRequest.add();
        addRequest.execute(request);
    }
}
