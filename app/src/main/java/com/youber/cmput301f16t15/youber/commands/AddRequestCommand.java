package com.youber.cmput301f16t15.youber.commands;

import android.os.Parcel;
import android.os.Parcelable;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;

import java.util.ArrayList;

/**
 * Created by Jess on 2016-11-16.
 */

public class AddRequestCommand extends RequestCommand {

    public AddRequestCommand(Request r) {
        request = r;
    }

    @Override
    public void execute() {

        // we need to do this check when the request we want to update is
        // a driver wanting to offer a ride, but the rider has already selected a different driver
        if(request.getCurrentStatus() == Request.RequestStatus.acceptedByDrivers)
        {
            try {
                ArrayList<Request> requests = ElasticSearchController.getAllRequests();
                RequestCollection esRequests = new RequestCollection();
                esRequests.addAll(requests);

                Request esRequest = esRequests.getRequestByUUID(request.getUUID());
                if(esRequest.getCurrentStatus() != Request.RequestStatus.opened ||
                        esRequest.getCurrentStatus() != Request.RequestStatus.acceptedByDrivers) {
                    executionState = true;
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ElasticSearchRequest.add adder = new ElasticSearchRequest.add();
        adder.execute(request);
        executionState = true;
    }

    @Override
    public void unexecute() {

    }
}
