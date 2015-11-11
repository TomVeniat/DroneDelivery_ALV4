/**
 * Created by Quentin on 11/6/2015.
 */

var droneSimulator=require('./drone_simulator'),
    properties = require("../properties");


var Drone  = function(id, event) {
    this.id = id;
    this.event = event;

    this.run = function(totalTime, pingFrequency) {
        totalTime = totalTime || properties.SIMULATION_TIME;
        pingFrequency = pingFrequency || properties.PING_FREQUENCY;

        for(var k = 0; k < totalTime/pingFrequency; k++) {
            setTimeout(function () {
                console.log(k);
                droneSimulator.simulate()
            }, pingFrequency);
        }
        console.log(id);
    };
};
Drone.prototype = new Drone();


exports.drone = Drone;

