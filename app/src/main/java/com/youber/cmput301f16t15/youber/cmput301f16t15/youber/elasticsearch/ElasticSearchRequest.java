package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.elasticsearch;

import android.os.AsyncTask;
import android.util.Log;

import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.Request;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by aphilips on 11/7/16.
 */
public class ElasticSearchRequest extends ElasticSearch {

    /**
     * The type Add.
     */
    public static class add extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();


            for (Request request : requests) {
                Index index = new Index.Builder(request).index("youber").type("request").build();

                try {
                    DocumentResult result = getClient().execute(index);
//                    if(result.isSucceeded()) {
//                        tweet.setId(result.getId());
//                    }
                } catch (Exception e) {
                    Log.i("Error", "The app failed to build and sent the tweets");
                }
            }

            return null;
        }
    }

    /**
     * The type Get objects.
     */
    public static class getObjects extends AsyncTask<String, Void, ArrayList<Request>> {

        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> requests = new ArrayList<Request>();

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex("youber")
                    .addType("request")
                    .build();

            try {
                SearchResult result = getClient().execute(search);
                if(result.isSucceeded()) {
                    List<Request> foundTweets = result.getSourceAsObjectList(Request.class);
                    requests.addAll(foundTweets);
                }
                else {
                    Log.i("Error", "The search execited but it didnt work");
                }
            }
            catch(Exception e) {
                Log.i("Error", "Executing the get tweets method failed");
            }

            return requests;
        }
    }

}
