/*
	Module responsible for handling drone messages.
*/

var express = require("express"),
	router = express.Router(),
	kafkaPusher = require("./kafka_pusher");

router.post('/drone_message', function (req, res) {
	var message = req.body;
	console.log(message);
	// TODO check message validity
//	trackingProcessor.processTracking(message); ancien code

	// FORME DU JSON : {"id":"0","event":"drone","deliveryNumber":"0"}
	kafkaPusher.pushEvent(message);
	
	res.end();
});


module.exports = router;