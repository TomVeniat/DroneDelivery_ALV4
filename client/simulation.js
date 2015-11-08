/**
 * Created by Quentin on 11/8/2015.
 */

/**
 * Here we create an object simulation with a given number of drone.
 *
  * @param numberOfDrone
 * @constructor
 */
var Simulation = function(numberOfDrone) {
    this.numberDrone = numberOfDrone;
    this.drones = [numberOfDrone]

    /**
     * This function will start the simulation
     * TODO : Now the simulation just start all the drone, but it should start 10 drone per second instead
     */
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

    /**
     * This method is used in graph.js to display the number of drone started.
     * @returns {Array} the array of all the started drones.
     */
    this.getDrones = function () {
        return this.drones;
    }
};

Simulation.prototype = new Simulation();
