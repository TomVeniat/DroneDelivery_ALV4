/**
 * Created by Quentin on 11/7/2015.
 */




function run() {
    console.log("derp");
    var drones = [1600];
    var simulation = new Simulation(1600);
    start(simulation);


    setTimeout(function() {
        simulation.run();
    }, 0);



  //  var drone = new Drone("5","usual_tracking_message");
   // drone.run();

};

function derp() {
    console.log("herp");
}