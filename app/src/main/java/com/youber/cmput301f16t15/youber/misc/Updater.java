package com.youber.cmput301f16t15.youber.misc;

import com.youber.cmput301f16t15.youber.commands.Command;

/**
 * Created by Jess on 2016-11-16.
 */

public class Updater implements Listener {

    @Override
    public void update(Command c) {
        c.execute();
    }
}
