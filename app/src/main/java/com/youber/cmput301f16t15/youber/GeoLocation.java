package com.youber.cmput301f16t15.youber;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Reem on 2016-10-13.
 */
public class GeoLocation implements Serializable {
    private double lat;
    private double lon;


    public GeoLocation(double lat, double lon)
    {
        this.lat=lat;
        this.lon=lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(!GeoLocation.class.isAssignableFrom(o.getClass())){
            return false;
        }
        final GeoLocation otherGeoLocation= (GeoLocation) o;
        if(Math.abs(otherGeoLocation.getLat()-this.lat)<0.00001&&Math.abs(otherGeoLocation.getLon()-this.lon)<0.00001){
            return true;
        }
        return false;
    }


}
