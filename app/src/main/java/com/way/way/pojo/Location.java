package com.way.way.pojo;

/**
 * Created by anurag.yadav on 4/14/17.
 */

public class Location {
    private String username;
    private Double latitude;
    private Double longitude;



    public Location(String username, Double latitude, Double longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
