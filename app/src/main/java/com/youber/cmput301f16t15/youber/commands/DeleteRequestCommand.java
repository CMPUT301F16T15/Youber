package com.youber.cmput301f16t15.youber.commands;


import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.requests.Request;

/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class DeleteRequestCommand extends RequestCommand {
    public DeleteRequestCommand(Request r) {
        request = r;
    }

    @Override
    public void execute() {
        ElasticSearchRequest.delete deleter = new ElasticSearchRequest.delete();
        deleter.execute(request);
        executionState = true;
    }

    @Override
    public void unexecute() {

    }
}
