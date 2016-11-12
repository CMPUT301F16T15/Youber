package com.youber.cmput301f16t15.youber;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;

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
import io.searchbox.core.SearchResult;
import io.searchbox.indices.mapping.PutMapping;

import static com.youber.cmput301f16t15.youber.ElasticSearch.getClient;
import static com.youber.cmput301f16t15.youber.ElasticSearch.verifySettings;

/**
 * Created by aphilips on 11/7/16.
 */
public class ElasticSearchRequest extends ElasticSearch{

    /**
     * The type Add.
     */
    @Override
    public void update(){
        HashSet<UUID> requestUUIDs = UserController.getUser().getRequestUUIDs();
        RequestCollection requestCollection = RequestCollectionsController.getRequestCollection();

        for (UUID uuid:requestUUIDs ) {
            ElasticSearchRequest.add adder = new ElasticSearchRequest.add();
            Request request = requestCollection.get(uuid);
            adder.execute(request);
        }


    }

    public static class add extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... requests) {
            verifySettings();

            for (Request request : requests) {
                Index index = new Index.Builder(request).index("youber").type("request").build();

                try {
                    DocumentResult result = getClient().execute(index);
                } catch (Exception e) {
                    Log.i("Error", "The app failed to build and sent the tweets " +e.toString());
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
            Get search = new Get.Builder("youber", search_parameters[0]).type("request").build();

            try {
                JestResult result = getClient().execute(search);
                if(result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    requests.addAll(foundRequests);

                }
                else {
                    Log.i("Error", "The search execited but it didnt work");
                }
            }
            catch(Exception e) {
                Log.i("Error", "Executing the get tweets method failed" + e.toString());
            }

            return requests;
        }
    }

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

    public static RequestCollection getRequestCollection(HashSet<UUID> hashSet){
        RequestCollection requestCollection= new RequestCollection();

        if(hashSet==null){return requestCollection;}

        for (UUID uuid:hashSet) {
            getObjects searchRequest= new getObjects();
            searchRequest.execute(uuid.toString());
            try{
                ArrayList<Request> requests=searchRequest.get();
                if(requests.size()==1){
                    Log.i("Request",requests.get(0).toString());
                }
                requestCollection.add(requests.get(0));
            }
            catch (ExecutionException e){

            }
            catch (InterruptedException e){

            }
        }

        return requestCollection;
    }
//    public static RequestCollection getRequestCollectionFrom(GeoLocation g1){
//        RequestCollection requestCollection = new RequestCollection();
//        if(g1==null){return requestCollection;}
//        String query= "";
//
//        Search search = new Search.TemplateBuilder(query)
//
//        return  requestCollection;
//    }
    public static void putmappingsetup(){
        verifySettings();
        PutMapping putMapping =new PutMapping.Builder(
                    "youber",
                    "request",
                "{\"request\" : { \"properties\" : { \"startLocation\" : {\"type\" : \"geo_point\"}}}}"
        ).build();
        try{
           JestResult jr= getClient().execute(putMapping);
        }catch(Exception e){Log.i("put map","failed cause :" + jr.toString());}

    }

}
