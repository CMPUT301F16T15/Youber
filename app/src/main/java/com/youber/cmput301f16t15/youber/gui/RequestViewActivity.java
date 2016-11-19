package com.youber.cmput301f16t15.youber.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.youber.cmput301f16t15.youber.R;

import com.youber.cmput301f16t15.youber.commands.MacroCommand;

import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The type Request view activity.
 * @see Request
 */
public class
RequestViewActivity extends AppCompatActivity {
    /**
     * The Request list view.
     */
    ListView requestListView;
    ArrayList<Request> requestArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestListView = (ListView)findViewById(R.id.requestListView);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {



                if (UserController.getUser().getCurrentUserType().equals(User.UserType.rider)) {
                    Intent intent = new Intent(RequestViewActivity.this, RequestActivity.class);
                    intent.putExtra("uuid", requestArray.get(i).getUUID());
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(RequestViewActivity.this, DriverViewRequestActivity.class);
                    intent.putExtra("uuid", requestArray.get(i).getUUID());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // grab the requests!
        RequestCollection requests = RequestCollectionsController.getRequestCollection();
        requestArray = new ArrayList<Request>();
        requestArray.addAll(requests.values());


//        http://stackoverflow.com/questions/20809272/android-change-listview-item-text-color
        ArrayAdapter<Request> adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                UUID requestUUID = requestArray.get(position).getUUID();


                if (MacroCommand.isRequestContained(requestUUID)) {
                    view.setBackgroundColor(Color.LTGRAY);
                }
                if (requestArray.get(position).getCurrentStatus().equals(Request.RequestStatus.acceptedByDrivers))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.orange));
                }
                else if (requestArray.get(position).getCurrentStatus().equals(Request.RequestStatus.riderSelectedDriver))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.yellow));

                }
                else if (requestArray.get(position).getCurrentStatus().equals(Request.RequestStatus.paid))
                {
                    view.setBackgroundColor(getResources().getColor(R.color.paleGreen));

                }
                else if (requestArray.get(position).getCurrentStatus().equals(Request.RequestStatus.completed)) {
                    view.setBackgroundColor(getResources().getColor(R.color.green));

                }
                return view;
            }
        };

        requestListView.setAdapter(adapter);




    }

    @Override
    protected void onResume() {
        super.onResume();
        MacroCommand.execute(); // try to execute on resume!
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
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
        else if (id == R.id.action_switch_user)
        {
            Intent intent = new Intent(this, UserTypeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
