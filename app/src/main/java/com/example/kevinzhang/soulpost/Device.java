package com.example.kevinzhang.soulpost;

/**
 * Created by kevinzhang on 2016-12-10.
 */

public class Device {
    double longitude;
    double latitude;
    double radius;
    String token;

    public Device(double longitude, double latitude, double radius, String token) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.token = token;
    }
}
