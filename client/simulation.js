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
     * TODO : Now the simulation just start all the drone, but it should start 10 drone per second instead(OK?)
     */
    this.run = function () {
        //for (var j = 0; j < this.numberDrone; j++) {
            window.j =0;
            var self = this;
            //Every second start 10 drones and stop it when the number is limited
            var drones = setInterval(function () {
                if(j>=this.numberDrone) {
                    clearInterval(drones);
                }
                var myDrone = new Drone(window.j, "usual_tracking_message");
                console.log("j:" + window.j);
                self.drones[window.j++] = myDrone;
                myDrone.run();
            }, 100);
       // }
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
