package quentinSandbox;

import org.json.simple.JSONObject;

/**
 * Created by quentin on 02/02/16.
 */
public class TrackingMock {

    public static JSONObject trackingCheck(JSONObject jsonObject) {
        System.out.println(jsonObject.get("lat"));
        Double lat = (Double) jsonObject.get("lat");

        if(lat > 100) {
            jsonObject.put("event", "flight_reported");
        }
        return jsonObject;
    }
}
