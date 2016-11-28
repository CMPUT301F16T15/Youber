package com.youber.cmput301f16t15.youber.commands;

import com.youber.cmput301f16t15.youber.requests.Request;

/**
 * Created by Jess on 2016-11-16.
 *
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * This is an abstract class that serves as a parent class to AddRequestCommand and
 * DeleteRequestCommand. It also implements the Command interface.
 * @see AddRequestCommand
 * @see DeleteRequestCommand
 * @see Command
 */

public abstract class RequestCommand implements Command {

    Request request;
    Boolean executionState = false;

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
