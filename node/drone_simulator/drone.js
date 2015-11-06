/**
 * Created by Quentin on 11/6/2015.
 */

var droneSimulator=require('./drone_simulator');


var Drone  = function(id, event) {
    this.id = id;
    this.event = event;

    this.run = function() {
        /** Boucle qui va jusque 36 car 5 secondes entre chaque appelle et une durée de 3 minutes **/
        for(var k = 0; k < 36; k++) {
            setTimeout(function () {
                droneSimulator.simulate()
            }, 5000);
        }
        console.log(id);
    };
};
Drone.prototype = new Drone();


exports.drone = Drone;

