/**
 * Created by Quentin on 11/8/2015.
 */

/**
 * Here we create an objet Drone with an id and an event.
  * @param id of the drone
 * @param event that he will to the server
 * @constructor ??
 */
var Drone = function(id, event) {
    this.id = id;
    this.event = event;

    /**
     * This function start a drone. Now the drone just send one message, but he is supposed
     * to send 1 message very 5 seconds. TODO !
     */
    this.run = function() {
        sendInfo();
        setTimeout(function() {
            console.log("HEEEEHO")
        }, 5000)
    }

    /**
     * This method will send the information of the drone with the given id and event.
     */
    function sendInfo() {
        $.post("http://localhost:9000/drone_message",
            {
                "id": id,
                "lat": 10.1,
                "lon": 12.1,
                "alt": 15.0,
                "fuel": 99,
                "event": event
            }).done(function(data) {
                console.log("derp");
            });
    }
};

Drone.prototype = new Drone();
