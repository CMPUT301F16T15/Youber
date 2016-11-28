package com.youber.cmput301f16t15.youber.elasticsearch;
import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;


import java.util.ArrayList;


/**
 * Created by aphilips on 11/7/16.
 *
 * <p>
 *     This is the class we use to store users and requests on a specified web server as shown
 *     in class.
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * @see com.youber.cmput301f16t15.youber.requests.Request
 * @see com.youber.cmput301f16t15.youber.users.User
 * @see ElasticSearchUser
 * @see ElasticSearchRequest
 * @see ElasticSearchController
 */



public class ElasticSearch {

    private static JestDroidClient jestDroidClient;

    /**
     * The type Add.
     *
     * @param <T> the type parameter
     */
    public static abstract class add<T> extends AsyncTask<T,Void,Void>{
        @Override
        protected abstract Void doInBackground(T...objects);
    }

    /**
     * The type Get objects.
     *
     * @param <T> the type parameter
     */
    public static abstract class getObjects<T> extends AsyncTask<String, Void, ArrayList<T>>{
        @Override
        protected abstract ArrayList<T> doInBackground(String... Search);

    }

    /**
     * Verify settings.
     */
    public static void verifySettings(){
        if(jestDroidClient==null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            jestDroidClient= (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Get client jest droid client.
     *
     * @return the jest droid client
     */
    public  static JestDroidClient getClient(){
        return jestDroidClient;
    }
}
