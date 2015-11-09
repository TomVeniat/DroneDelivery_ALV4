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
    var drones = [numberOfDrone],
        runningDrones = 0;

    /**
     * This function will start the simulation
     * TODO : Now the simulation just start all the drone, but it should start 10 drone per second instead(OK?)
     */
    this.run = function () {

        //Every second start 10 drones and stop it when the number is limited
        var dronesInterval = setInterval(function () {
            console.log( runningDrones + " out of "+ numberOfDrone + " are currently running");

            if (runningDrones >= numberOfDrone) {
                clearInterval(dronesInterval);
            }

            for (var i = 0 ; i < 10 ; ++i) {

                var myDrone = new Drone(runningDrones, "usual_tracking_message");

                drones[runningDrones++] = myDrone;

                myDrone.run();

            }

        }, 1000);
    };

    /**
     * This method is used in graph.js to display the number of drone started.
     * @returns {Array} the array of all the started drones.
     */
    this.getDrones = function () {
        return drones;
    }
};

Simulation.prototype = new Simulation();
