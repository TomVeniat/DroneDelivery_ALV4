/**
 * Created by Quentin on 11/7/2015.
 */


/**
 * This function run the simulation. It will create a simulation with a given number
 * of drone. Then the function start will create the graph. Finally we run the simulation
 * asynchronously.
 */
function run() {
    compteur = 0;

    var simulation = new Simulation(300);
    start(simulation);
}

