/*
 * Main app.
 */

 /*
  Call the simulator every 5 seconds
  */
var droneSimulator=require('./drone_simulator');
var test = require("./drone");

//setInterval(droneSimulator.simulate, 500);

//var derp = new test.drone("test","test");
//derp.run();

var table = [120];


for(var j = 0; j < 100; j++) {
  var caca = new test.drone(j,j);
  table[j] = caca;
}

for(var j = 0; j < 100; j++) {
  table[j].run();
}
