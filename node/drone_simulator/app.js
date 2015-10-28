/*
	Main app.
*/

var droneSimulator = require("./drone_simulator"),
	app = droneSimulator.server();

app.listen(8280, "127.0.0.1");