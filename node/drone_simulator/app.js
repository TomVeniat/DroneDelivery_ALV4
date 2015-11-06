/*
 * Main app.
 */

 /*
  Call the simulator every 5 seconds
  */
var drone = require("./drone");

//setInterval(droneSimulator.simulate, 500);

var table = [1600];


for(var j = 0; j < 1600; j++) {
  var myDrone = new drone.drone(j,j);
  table[j] = myDrone;
}

for(var j = 0; j < 1600; j++) {
  table[j].run();
}
