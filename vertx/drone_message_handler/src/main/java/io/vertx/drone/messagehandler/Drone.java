package io.vertx.drone.messagehandler;

/**
 * Created by SUN on 18/12/2015.
 */

public class Drone {
    private int id;
    private double lat;
    private double lon;
    private double alt;
    private double fuel;
    private String event;

    public Drone(int id) {
        this.id = id;
    }

    public Drone(int id, double lat, double lon, double alt, double fuel, String event) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.fuel = fuel;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"lat\":" + lat +
                ",\"lon\":" + lon +
                ",\"alt\":" + alt +
                ",\"fuel\":" + fuel +
                ",\"event\":\"" + event + "\"" +
                '}';
    }
}
