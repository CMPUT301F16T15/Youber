package com.youber.cmput301f16t15.youber.elasticsearch;

import android.os.AsyncTask;
import android.util.Log;

import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.mapping.PutMapping;

/**
 * Created by aphilips on 11/7/16.
 * <p>
 *     Subclass of elastic search that checks the webserver for matching requests
 * </p>
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see ElasticSearch
 * @see ElasticSearchController
 */
public class ElasticSearchRequest extends ElasticSearch{
    /**
     * Add a request to the server.
     */
    public static class add extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();

            for (Request request : requests) {
                Index index = new Index.Builder(request).index("youber").type("request").build();

                try {
                    DocumentResult result = getClient().execute(index);
                } catch (Exception e) {
                    Log.i("Error", "The app failed to build and and add request");
                }
            }

            return null;
        }
    }


    /**
     * Search parameters for our query
     * @see ElasticSearchController
     */
    public static class getObjectsBySearch extends AsyncTask<String, Void, ArrayList<Request>> {


        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();
            Search search = new Search.Builder(search_parameters[0]).addIndex("youber").addType("request").build();
            JestResult result = null;
            ArrayList<Request> requests = new ArrayList<Request>();
            try {
                result = getClient().execute(search);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.isSucceeded()) {
                List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                requests.addAll(foundRequests);
                Log.i("Works", "The search executed it work");

            } else {
                Log.i("Error", "The search executed but it didnt work");
                Log.i("jest error",result.toString());
            }

            return requests;
        }
    }
    /**
     * Running the search
     * @see getObjectsBySearch
     */
    public static class getObjects extends AsyncTask<String, Void, ArrayList<Request>> {

        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> requests = new ArrayList<Request>();
            JestResult result = null;
            try
            {
                if (search_parameters.length==0)
                {
                    Search search = new Search.Builder("").addIndex("youber").addType("request").build();
                    result = getClient().execute(search);
                }
                else
                {
                    Get search = new Get.Builder("youber", search_parameters[0]).type("request").build();
                    result = getClient().execute(search);
                }

                if(result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    requests.addAll(foundRequests);

                }
                else {
                    Log.i("Error", "The search executed but it didnt work");
                }
            }
            catch(Exception e) {
                Log.i("Error", "Executing the get requests method failed" + e.toString());
            }

            return requests;
        }
    }

    /**
     * Delete a request from the server.
     *
     * @see add
     */
    public static class delete extends AsyncTask<Request, Void, Void> {
        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();

            for(Request r : requests) {
                Delete delete = new Delete.Builder(r.getUUID().toString()).index("youber").type("request").build();

                try {
                    DocumentResult result = getClient().execute(delete);
                } catch (Exception e) {
                    Log.i("Error", "Deleting request failed " + e.toString());
                }
            }

            return null;
        }
    }

    /**
     * Get request collection
     *
     * @param hashSet the hash set
     * @return the request collection
     */
    public static RequestCollection getRequestCollection(HashSet<UUID> hashSet){
        RequestCollection requestCollection= new RequestCollection();

        if(hashSet==null){return requestCollection;}

        for (UUID uuid:hashSet) {
            getObjects searchRequest= new getObjects();
            searchRequest.execute(uuid.toString());
            try{
                ArrayList<Request> requests=searchRequest.get();
                if(requests.size()==1){
                    Log.i("Request!",requests.get(0).toString());
                    Request request = requests.get(0);
                    requestCollection.add(requests.get(0));
                }
            }
            catch (ExecutionException e){

            }
            catch (InterruptedException e){

            }
        }

        return requestCollection;
    }

    /**
     * Add a request to the server using putmapping
     */
    public static class addPutMap extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... putmaps) {
            verifySettings();

            for (String putmap : putmaps) {
                PutMapping putMapping =new PutMapping.Builder(
                        "youber",
                        "request",
                        "{\"request\" : { \"properties\" : { \""+putmap+"\" : {\"type\" : \"geo_point\"}}}}"
                ).build();
                JestResult jr=null;
                try{
                    jr= getClient().execute(putMapping);
                }catch(Exception e){Log.i("put map","failed cause :" + jr.toString());}
            }

            return null;
        }
    }
}
