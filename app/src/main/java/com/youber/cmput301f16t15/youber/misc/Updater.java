package com.youber.cmput301f16t15.youber.misc;

import com.youber.cmput301f16t15.youber.commands.Command;
import com.youber.cmput301f16t15.youber.commands.MacroCommand;

/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class Updater implements Listener {

    @Override
    public void update(Command c) {
        MacroCommand.addCommand(c);
    }
}
