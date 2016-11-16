package com.youber.cmput301f16t15.youber.commands;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.youber.cmput301f16t15.youber.commands.Command;

import java.util.ArrayList;

/**
 * Created by Jess on 2016-11-16.
 */

public class MacroCommand {
    private static ArrayList<Command> commands;
    private static Context context;

    public static void setContext(Context c) {
        context = c;
    }

    public static void addCommand(Command c) {
        commands.add(c);
        execute();
    }

    public static void execute() {
        for(Command c : commands) {
            if(c.getExecutionState() == false)
                c.execute();

        }


    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

