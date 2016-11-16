package com.youber.cmput301f16t15.youber.commands;

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
        ElasticSearchRequest.add adder = new ElasticSearchRequest.add();
        adder.execute(request);
        executionState = true;
    }

    @Override
    public void unexecute() {

    }
}
