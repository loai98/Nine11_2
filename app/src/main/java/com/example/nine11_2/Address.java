package com.example.nine11_2;

public class Address {
    String lat , lon;

    public Address(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Address() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
