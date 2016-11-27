package com.youber.cmput301f16t15.youber.commands;



/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public interface Command {
    void execute();
    void unexecute();
    Boolean isExecuted();
}
