"use strict"

var http = require("http"),
	host = process.argv[2];

var options = {
	hostname: host.slice(0, host.indexOf(":")),
	port: host.slice(host.indexOf(":") + 1, host.length),
	path: "/drone_message",
	method: "POST",
	headers: {
		"Content-Type": "application/json",
		"Content-Length": 0
	}
}

class Drone {

	constructor (id, scenario) {
		this._id = id;
		this._scenario = scenario;
	}

	get id () { return this._id; }
	get scenario () { return this._scenario; }

	run () {
		var n = 0, req, message, scenario = this._scenario, id = this._id;

		var interval = setInterval(function () {
				if (n < scenario.length) {
					message = JSON.stringify(buildMessage(id, scenario[n], n, scenario.length));
					options.headers["Content-Length"] = message.length;
					req = http.request(options, function (res) {
						console.log("posting message:", message);
					});
					req.write(message);
					req.end();
					n++;
				}
				else {
					clearInterval(interval);
				}
			}, 5000);
		console.log("running!");
	}
}

function buildMessage(id, scenarioItem, n, scenarioLength) {
	var message = scenarioItem;

	message.id = "" + id;
	if (n == 0)
		message.event = "start_flight";
	else if (n == scenarioLength)
		message.event = "end_flight";
	else if (n == scenarioLength / 2)
		message.event = "delivery";
	else message.event = "usual_tracking_message";

	return message;
}

function generateScenario(failing) {
	var scenario = [],
		//messages = Math.floor(Math.random() * 200) + 1,
		messages = 36,
		finalFuel = Math.random() * 100,
		lon = 10.7,
		lat = 12.2,
		latIncr = 0.5,
		alt,
		fuel;

	if (failing) {
		var maxLat = 100 + (Math.random() * 50 + 1);

		latIncr = (maxLat - lat) / (messages / 2); 
	}

	for (var i = 0; i < messages; i++) {

		if (i == 0 || i == messages - 1)
			alt = 0.0;
		else alt = 10.0;

		if (i < messages / 2) {
			lon++;
			lat += latIncr;
		}
		else {
			lon--;
			if (!failing)
				lat -= latIncr;
		}

		fuel = (messages - i) / messages * (100 - finalFuel) + finalFuel;
		//console.log(lon, lat, alt, fuel);
		scenario.push({ lon: "" + lon, lat: "" + lat, alt: "" + alt, fuel: "" + fuel });
	};

	return scenario;
}

var scenario0 = generateScenario(false),
	scenario1 = generateScenario(true);

new Drone(25, scenario0).run();
new Drone(20, scenario1).run();