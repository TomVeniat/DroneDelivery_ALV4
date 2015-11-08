/**
 * Created by Quentin on 11/8/2015.
 */

var Simulation = function(numberOfDrone) {
    this.numberDrone = numberOfDrone;
    this.drones = [numberOfDrone]

    this.run = function () {
        for (var j = 0; j < this.numberDrone; j++) {
            var self = this;
            setTimeout(function () {
                var myDrone = new Drone(j, "usual_tracking_message");
                self.drones[j] = myDrone;
                myDrone.run();
            }, 0)
        }
    }

    this.getDrones = function () {
        return this.drones;
    }
};

Simulation.prototype = new Simulation();
