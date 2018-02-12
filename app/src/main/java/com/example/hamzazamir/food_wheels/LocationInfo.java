package com.example.hamzazamir.food_wheels;

/**
 * Created by HamzaZamir on 2/10/2018.
 */

public class LocationInfo {
    private double longi;
    private double lati;
    private String location_id;

    public LocationInfo(double longi, double lati,String location_id){
        this.longi = longi;
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

}
