/*
	Module responsible for handling drone messages.
*/

var express = require("express"),
	router = express.Router(),
	kafkaPusher = require("./kafka_pusher");

/*
	Awaits for POST requests with body containing a JSON object
	representing a drone message like the following:
	{
		"id": 0,
		"lat": 10.1,
		"lon": 12.1,
		"alt": 15.0,
		"fuel": 99,
		"event": "usual_tracking_message"
	}
	"event" property is optional, of course the values may differ
*/
router.post('/drone_message', function (req, res) {
	var message = req.body;
	
	console.log(message);
	// TODO check message validity
	kafkaPusher.pushMessage(JSON.stringify(message));
	res.end();
});


module.exports = router;