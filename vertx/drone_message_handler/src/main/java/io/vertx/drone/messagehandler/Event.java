package io.vertx.drone.messagehandler;

/**
 * Created by SUN on 17/01/2016.
 */
public enum Event {
    TAKE_OFF("take off");
    private String event;
    private Event(String event) {
        this.event = event;
    }
    public String getEvent() {
        return this.event;
    }
}
