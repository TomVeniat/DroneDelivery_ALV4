/**
 * Created by Quentin on 11/6/2015.
 */

var droneSimulator=require('./drone_simulator');


var Drone  = function(id, event) {
    this.id = id;
    this.event = event;

    this.run = function() {
        for(var k = 0; k < 10; k++) {
            setTimeout(function () {
                droneSimulator.simulate()
            }, 10);
        }
        console.log(id);
    };
};
Drone.prototype = new Drone();


exports.drone = Drone;