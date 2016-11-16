package com.youber.cmput301f16t15.youber.commands;

import com.youber.cmput301f16t15.youber.commands.Command;

import java.util.ArrayList;

/**
 * Created by Jess on 2016-11-16.
 */

public class MacroCommand {
    private static ArrayList<Command> commands;

    public static void addCommand(Command c) {
        commands.add(c);
    }

    public static void execute() {
        for(Command c : commands) {
            c.execute();
        }
    }
}

