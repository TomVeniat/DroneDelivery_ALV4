/**
 * Created by Quentin on 11/8/2015.
 */

/**
 * Here we create an object simulation with a given number of drone.
 *
  * @param numberOfDrone
 * @constructor
 */
var Simulation = function(simulationParams) {

    simulationParams.nbDrones = simulationParams.nbDrones || 100;
    simulationParams.dronePingFrequency = simulationParams.dronePingFrequency || 1;
    simulationParams.droneSimulationTime = simulationParams.droneSimulationTime || 300;
    simulationParams.timeBetweenLaunches = simulationParams.timeBetweenLaunches || 0;
    simulationParams.launchSize = simulationParams.launchSize || 1;


    var drones = [simulationParams.nbDrones],
        runningDrones = 0;

    /**
     * This function will start the simulation
     * TODO : Now the simulation just start all the drone, but it should start 10 drone per second instead(OK?)
     */
    this.run = function () {

        //Every second start {launchSize} drones and stop it when the total number of drones reach {nbDrones}
        var dronesInterval =
            setInterval(function startDrones() {

                for (var i = 0 ; i < simulationParams.launchSize ; ++i) {

                    var myDrone = new Drone(runningDrones, "usual_tracking_message", simulationParams.droneSimulationLength, simulationParams.dronePingFrequency);

                    drones[runningDrones++] = myDrone;

                    myDrone.run();

                }

                console.log( runningDrones + " out of "+ simulationParams.nbDrones + " are currently running");

                if (runningDrones >= simulationParams.nbDrones) {
                    clearInterval(dronesInterval);
                }

            }, simulationParams.timeBetweenLaunches);
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
