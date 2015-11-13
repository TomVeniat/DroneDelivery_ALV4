/**
 * Created by Quentin on 11/6/2015.
 */

var droneSimulator=require('./drone_simulator'),
    properties = require("../properties");


var Drone  = function(id, event) {
    this.id = id;
    this.event = event;

    this.run = function(totalTime, pingPeriodicity) {

        totalTime = totalTime || properties.SIMULATION_TIME;

        pingPeriodicity = pingPeriodicity || properties.PING_PERIODICITY;

        for(var k = 0; k < totalTime / pingPeriodicity; k++) {
            setTimeout(function () {
                console.log(k);
                droneSimulator.simulate();
            }, pingPeriodicity);
        }

        console.log(id);

    };
};
Drone.prototype = new Drone();


exports.drone = Drone;

