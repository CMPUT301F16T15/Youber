package com.youber.cmput301f16t15.youber.commands;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;

/**
 * Created by Jess on 2016-11-16.
 */

public class AddRequestCommand extends RequestCommand {

    public AddRequestCommand(Request r) {
        request = r;
    }

    @Override
    public void execute() {
        try {
            ElasticSearchController.setupPutmap();
            // This is for the specific case where we were offline and add an offer as a driver
            // but this request had already gotten a selected driver (someone else)
            Request esRequest = ElasticSearchController.getRequest(request.getUUID());

            if(esRequest != null && !esRequest.getDriverUsernameID().isEmpty() && request.getDriverUsernameID().isEmpty()) {
                executionState = true;
                return;
            }

            ElasticSearchRequest.add adder = new ElasticSearchRequest.add();
            adder.execute(request);
            executionState = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unexecute() {

    }
}
