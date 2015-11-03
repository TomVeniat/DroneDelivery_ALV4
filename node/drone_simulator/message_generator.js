/*
* This function generates the message of the drone
*/
function generator() {
	var msg = {};
	msg["id"]=0;
	msg["lat"]=10.1;
	msg["lon"]= 12.1;
	msg["alt"]= 15.0;
	msg["fuel"]= 99;
	msg["event"]= "take off";

	return msg;
}

exports.generator=generator;