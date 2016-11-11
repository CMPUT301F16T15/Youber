package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests;

import java.io.Serializable;

/**
 * Created by Reem on 2016-10-13.
 */
public class GeoLocation implements Serializable {
    private double lat;
    private double lon;

    /**
     * Instantiates a new Geo location.
     */
    public GeoLocation() {

    }

    /**
     * Instantiates a new Geo location.
     *
     * @param lat the lat
     * @param lon the lon
     */
    public GeoLocation(double lat, double lon)
    {
        this.lat=lat;
        this.lon=lon;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets lon.
     *
     * @return the lon
     */
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

    @Override
    public String toString() {
        return "Lat " + Double.toString(lat) + ", " + "Lon " + Double.toString(lon);
    }
}
