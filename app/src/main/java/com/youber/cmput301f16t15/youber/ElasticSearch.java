package com.youber.cmput301f16t15.youber;
import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;


/**
 * Created by aphilips on 11/7/16.
 */

public class ElasticSearch {
    private static JestDroidClient jestDroidClient;
    public static abstract class add<T> extends AsyncTask<T,Void,Void>{
        @Override
        protected abstract Void doInBackground(T...objects);
    }

    public static abstract class getObjects<T> extends AsyncTask<String, Void, ArrayList<T>>{
        @Override
        protected abstract ArrayList<T> doInBackground(String... Search);

    }

    public static void verifySettings(){
        if(jestDroidClient==null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            jestDroidClient= (JestDroidClient) factory.getObject();
        }
    }

    public  static JestDroidClient getClient(){
        return jestDroidClient;
    }
}
