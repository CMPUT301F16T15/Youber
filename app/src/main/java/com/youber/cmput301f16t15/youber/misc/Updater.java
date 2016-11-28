package com.youber.cmput301f16t15.youber.misc;

import com.youber.cmput301f16t15.youber.commands.Command;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;

/**
 * Created by Jess on 2016-11-16.
 *
 * This class implements a listener and is used alongside the command structure.
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * @see Command
 */

public class Updater implements Listener {

    @Override
    public void update(Command c) {
        MacroCommand.addCommand(c);
    }
}
