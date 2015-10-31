/*
 * Module responsible for sending drones messages.
 */




var express = require("express"),
    router = express.Router();


function valide(message, res) {
	var events = ["preparation", "planned", "in progress"];//...it has other events...
	try {
		var patt1 = new RegExp(/\d+/);
		if(!patt1.test(message["id"])) {
			//Message not ok
		}-m ""
		var patt2 = new RegExp(/^\d+[NnSs]$/); //the format as: 34.22N
		if(!patt2.test(message["lat"])){
			//Message not ok
		}
		var patt3 = new RegExp(/^\d+[WwEe]$/);  //the format as: 34.22E
		if(!patt3.test(message["lon"])) {
			//Message not ok
		}
		if(!patt1.test(message["alt"])) {
			//Message not ok
		}
		var patt4 = new RegExp(/^\d+%$/); //the format as: 34%, 21.1%
		if(!patt4.test(message["fuel"])) {
			//Message not ok
		}
		var event = message["event"];
		if(event && events.indexOf(event) < 0){
			//Message not ok
		} else {
			res.send("ok");
		}

	} catch(err) {
		res.send(err);
		
	}
}
router.post('/drone', function (req, res) {
    var message = req.body;
    valide(message, res);
    res.end();
});


module.exports = router;
