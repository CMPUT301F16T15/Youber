package com.youber.cmput301f16t15.youber.commands;

import com.youber.cmput301f16t15.youber.requests.Request;

import java.io.Serializable;

/**
 * Created by Jess on 2016-11-16.
 */

public abstract class RequestCommand implements Command {

    Request request;
    Boolean executionState = false;

//    public RequestCommand(Request r) {
//        request = r;
//    }

    @Override
    public abstract void execute();

    @Override
    public abstract void unexecute();

    @Override
    public Boolean isExecuted() {
        return executionState;
    }

    public Request getRequest() {
        return request;
    }
}
