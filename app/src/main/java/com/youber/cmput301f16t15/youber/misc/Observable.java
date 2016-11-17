package com.youber.cmput301f16t15.youber.misc;

import com.youber.cmput301f16t15.youber.commands.Command;

import java.util.ArrayList;

/**
 * Created by aphilips on 11/10/16.
 *
 * Observable class that works with the listener for elastic search
 * @see com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch
 * @see Listener
 */

public class Observable {
    ArrayList<Listener> listeners= new ArrayList<Listener>();

    public void notifyListeners(Command c) {
        for (Listener listener:listeners ) {
            listener.update(c);
        }
    }
    public void addListener(Listener listener){
        listeners.add(listener);
    }
}
