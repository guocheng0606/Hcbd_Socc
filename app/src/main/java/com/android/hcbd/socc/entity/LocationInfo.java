package com.android.hcbd.socc.entity;

/**
 * Created by guocheng on 2017/4/20.
 */

public class LocationInfo {
    private double latitude;
    private double longitude;
    private float radius;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
