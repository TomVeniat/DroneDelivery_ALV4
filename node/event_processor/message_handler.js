var express = require("express"),
	trackingProcessor = require("./tracking_processor"),
	router = express.Router();

router.post('/drone_message', function (req, res) {
	var message = req.body;

	// TODO check message validity
	trackingProcessor.processTracking(message);
	res.end();
});

module.exports = router;