var express = require("express"),
	bodyParser = require("body-parser"),
	trackingProcessor = require("./tracking_processor"),
	server = express();

server.use(bodyParser.json());

server.post('/drone_message', function (req, res) {
	var message = req.body;

	// TODO check message validity
	trackingProcessor.processTracking(message);
	res.end();
});

server.listen(8080);