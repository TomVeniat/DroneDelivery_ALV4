/*
 * Main app.
 */

 /*
  Call the simulator every 5 seconds
  */
var droneSimulator=require('./drone_simulator');
setInterval(droneSimulator.simulate, 5000);