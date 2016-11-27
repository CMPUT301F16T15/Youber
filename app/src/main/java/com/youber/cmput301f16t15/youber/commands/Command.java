package com.youber.cmput301f16t15.youber.commands;



/**
 * Created by Jess on 2016-11-16.
 */

public interface Command {
    void execute();
    void unexecute();
    Boolean isExecuted();
}
