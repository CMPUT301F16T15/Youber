package com.youber.cmput301f16t15.youber.commands;



/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * This interface is implemented by the other command classes and follows the command pattern
 * discussed in the lectures.
 *
 * @see com.youber.cmput301f16t15.youber.commands
 */

public interface Command {
    void execute();
    void unexecute();
    Boolean isExecuted();
}
