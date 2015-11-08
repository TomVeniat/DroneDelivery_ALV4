/**
 * Created by Quentin on 11/7/2015.
 */


function run() {
    console.log("derp");
    var drones = [1600];
    start();


    setTimeout(function() {
        for(var j = 0; j < 1600; j++) {
            var myDrone = new Drone(j,"usual_tracking_message");
            drones[j] = myDrone;
            myDrone.run();
        }
    }, 0);



  //  var drone = new Drone("5","usual_tracking_message");
   // drone.run();

};

function derp() {
    console.log("herp");
}