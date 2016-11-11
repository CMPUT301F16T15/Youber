package com.youber.cmput301f16t15.youber;

import java.util.ArrayList;

/**
 * Created by aphilips on 11/10/16.
 */

public class Observable {
    ArrayList<Listener> listeners= new ArrayList<Listener>();

    public void notifyListeners() {
        for (Listener listener:listeners ) {
            listener.update();

        }
    }
    public void addListener(Listener listener){
        listeners.add(listener);
    }
}
