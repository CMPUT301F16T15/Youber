package com.youber.cmput301f16t15.youber.misc;

import com.youber.cmput301f16t15.youber.commands.Command;

import java.io.Serializable;

/**
 * Created by Reem on 2016-11-09.
 * @see Serializable
 */

public interface Listener extends Serializable {

    void update(Command c); // take in Command, add, get, update?, delete inherit Command

}
