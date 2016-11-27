package com.youber.cmput301f16t15.youber.users;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Reem on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class RiderUserInfo {



    private HashSet<UUID> uuids;


    public RiderUserInfo()
    {
        uuids = new HashSet<UUID>();
    }


    public HashSet<UUID> getUUIDs() {
        return uuids;
    }

    public void setUUIDs(HashSet<UUID> uuids) {
        this.uuids = uuids;
    }
}
