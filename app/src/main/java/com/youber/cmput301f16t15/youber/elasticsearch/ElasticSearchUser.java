package com.youber.cmput301f16t15.youber.elasticsearch;

import android.os.AsyncTask;
import android.util.Log;

import com.youber.cmput301f16t15.youber.commands.Command;

import com.youber.cmput301f16t15.youber.exceptions.UserNotFoundException;

import com.youber.cmput301f16t15.youber.requests.Request;

import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;


/**
 * Created by aphilips on 11/7/16.
 *
 * <p>
 *     Subclass of elastic search that checks the webserver for matching users
 * </p>
 *
 * @see ElasticSearchController
 * @see ElasticSearch
 */
public class ElasticSearchUser extends ElasticSearch {
    /**
     * Add a request to the server.
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
                    Log.i("Error", "The app failed to build and add the user"+e.toString());
                }
            }

            return null;
        }
    }

    /**
     * Search parameters for our query
     * @see ElasticSearchController
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
                    Log.i("Error", "The search executed but it didnt work");
                }
            }
            catch(Exception e) {
                Log.i("Error", "Executing the get users method failed" + e.toString());
            }

            return users;
        }
    }

    public static User getUser(String username) throws UserNotFoundException {
        ElasticSearchUser.getObjects getUser= new ElasticSearchUser.getObjects();
        getUser.execute(username);
        User latestUser=null;
        try {

            ArrayList<User> users =  getUser.get();

            if(users.size() == 1){
                latestUser=users.get(0);
            }

            else{
                throw new UserNotFoundException();
            }


        } catch (Exception e) {

        }
        return latestUser;

    }

    public static class getUserByRequestUUID extends AsyncTask<String, Void, ArrayList<User>> {


        @Override
        protected ArrayList<User> doInBackground(String... params) {
            verifySettings();
            Search search = new Search.Builder(params[0]).addIndex("youber").addIndex("user").build();
            JestResult result = null;
            ArrayList<User> users = new ArrayList<User>();
            try {
                result = getClient().execute(search);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.isSucceeded()) {
                List<User> user = result.getSourceAsObjectList(User.class);
                users.addAll(user);


            } else {
                Log.i("Error", "The search executed but it didnt work");
                Log.i("jest error",result.toString());
            }

            return users;
        }
    }

    // ONLY TO BE USED FOR TESTING SO WE CLEAN UP
    public static class delete extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for(User u : users) {
                Delete delete = new Delete.Builder(u.getUsername()).index("youber").type("request").build();

                try {
                    DocumentResult result = getClient().execute(delete);
                } catch (Exception e) {
                    Log.i("Error", "Deleting user failed " + e.toString());
                }
            }

            return null;
        }
    }
}
