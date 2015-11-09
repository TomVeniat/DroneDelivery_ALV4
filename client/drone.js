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
     * This function starts a drone. Now the drone just send one message, but he is supposed
     * to send 1 message every 5 seconds. TODO !(OK?)
     */
    this.run = function() {
        //sendInfo();
        /*setTimeout(function() {
            console.log("HEEEEHO")
        }, 5000);*/

        //Every 5 secondes send a message
        setInterval(sendInfo, 5000);
    }

    /**
     * This method will send the information of the drone with the given id and event.
     */
    function sendInfo() {
        $.post("http://37.187.126.101:9000/drone_message",
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
