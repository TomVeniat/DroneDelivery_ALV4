package io.vertx.drone.messagehandler;

import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SUN on 17/01/2016.
 * This class validates the messages send by the drone
 */
public class DroneMessageParser {

    /**
     * @param obj message send by drone
     * @return the object represents Drone if the message is valide; otherwise, return null.
     */
    public static Drone parse(JsonObject obj) {
        /*
            Awaits for POST requests with body containing a JSON object
            representing a drone message like the following:
            {
                "id": 0,
                "lat": 10.1,
                "lon": 12.1,
                "alt": 15.0,
                "fuel": 99,
                "event": "usual_tracking_message"
            }
            "event" property is optional, of course the values may differ
        */
        try {
            int id = Integer.parseInt((String) obj.getValue("id"));
            double lat = Double.parseDouble((String) obj.getValue("lat"));
            double lon = Double.parseDouble((String) obj.getValue("lon"));
            double alt = Double.parseDouble((String) obj.getValue("alt"));
            double fuel = Double.parseDouble((String) obj.getValue("fuel"));
            if (lat < 0 || lon < 0 || alt < 0 || fuel < 0 || fuel > 100) {
                return null;
            }
            String event = (String) obj.getValue("event");

            if (event != null) {
                List<Event> events = Arrays.asList(Event.values());
                for (Event evt : events) {
                    if(evt.getEvent().equals(event)) {
                        return new Drone(id, lat, lon, alt, fuel, event);
                    }
                }
                return null;
            } else {
               return new Drone(id, lat, lon, alt, fuel, null);
            }

        } catch (Exception e) {
            return null;
        }
    }
}
