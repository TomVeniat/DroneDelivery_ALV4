/**
 * Created by Quentin on 11/8/2015.
 */

var Drone = function(id, event) {
    this.id = id;
    this.event = event;

    this.run = function() {
        sendInfo();
        setTimeout(function() {
            console.log("HEEEEHO")
        }, 5000)
    }

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
