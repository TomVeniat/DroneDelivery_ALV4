/*
	Main app.
*/

var express = require("express"),
	bodyParser = require("body-parser"),
	droneSimulator = require("./drone_simulator"),
	app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use("/", droneSimulator);

app.listen(8280);