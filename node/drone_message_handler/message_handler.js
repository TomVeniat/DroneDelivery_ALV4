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

router.get('/test', function (req, res) {
	console.log("got smtg");
	res.writeHead(200, {"Content-Type": "text/plain"});
	res.end("<3 Hello Team from drone_message_handler! <3\n");
});


module.exports = router;
