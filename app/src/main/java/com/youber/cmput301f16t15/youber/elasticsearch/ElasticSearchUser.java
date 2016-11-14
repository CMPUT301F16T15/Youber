package com.youber.cmput301f16t15.youber.elasticsearch;

import android.os.AsyncTask;
import android.util.Log;

import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;


/**
 * Created by aphilips on 11/7/16.
 */
public class ElasticSearchUser extends ElasticSearch {

    @Override
    public void update()
    {
        User user = UserController.getUser();
        ElasticSearchUser.add adder = new ElasticSearchUser.add();
        adder.execute(user);
    }
    /**
     * The type Add.
     */
    public static class add extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("youber").type("user").build();

                try {
                    DocumentResult result = getClient().execute(index);
                } catch (Exception e) {
                    Log.i("Error", "The app failed to build and sent the tweets"+e.toString());
                }
            }

            return null;
        }
    }

    /**
     * The type Get objects.
     */
    public static class getObjects extends AsyncTask<String, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();
            try{
                JestResult result = null;
                if (search_parameters.length==0)
                {
                    Search search = new Search.Builder("").addIndex("youber").addType("user").build();
                    result = getClient().execute(search);
                }
                else
                {
                    Get search = new Get.Builder("youber", search_parameters[0]).type("user").build();
                    result = getClient().execute(search);

                }

                if(result.isSucceeded()) {

                    List<User> foundUsers = result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);

                }
                else {
                    Log.i("Error", "The search execited but it didnt work");
                }
            }
            catch(Exception e) {
                Log.i("Error", "Executing the get tweets method failed" + e.toString());
            }

            return users;
        }
    }


}