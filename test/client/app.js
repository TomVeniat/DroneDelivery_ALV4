/**
 * Created by Quentin on 11/7/2015.
 */


/**
 * This function run the simulation. It will create a simulation with a given number
 * of drone. Then the function start will create the graph. Finally we run the simulation
 * asynchronously.
 */
function run() {
    totalPingFrequency = 0;
    gloablTotalMessages = 0;

    var simulationParams = {
        //Total number of drones that will be used during the simulation
        nbDrones : 1,

        //frequency with which a single drone will send pings (in message/second)
        dronePingFrequency : 0.5,

        //Number that rules a single drone simulation duration (in messages sent).
        droneSimulationLength : 2,

        //Time elapsed bewteen two drone launch (in ms)
        timeBetweenLaunches : 500,

        //Number of drone sent by Launch
        launchSize : 25 };


    var simulation = new Simulation(simulationParams);
    start(simulation);
}

