var kafkaPusher = require("./kafka_pusher");

exports.processEvent = function (droneEvent) {
	console.log("processing event", droneEvent);
	kafkaPusher.pushEvent(droneEvent);
}