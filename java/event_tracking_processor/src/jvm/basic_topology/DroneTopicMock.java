package basic_topology;

import java.util.HashMap;

/**
 * Created by quentin on 02/02/16.
 */
public class DroneTopicMock {

    private static HashMap<Long, String> MOCK;
    private static int DRONE_NUMBER = 20000;

    public static String getDroneTopic(Long droneNumber) {
        if(MOCK == null) {
          initMock();
        }
        return MOCK.get(droneNumber);
    }

    private static void initMock() {
        MOCK = new HashMap<>();
        for(int i = 0; i < DRONE_NUMBER; i++) {
            int test = DRONE_NUMBER - i;
            MOCK.put(new Long(test), new StringBuilder(""+i).toString());
        }
    }
}
