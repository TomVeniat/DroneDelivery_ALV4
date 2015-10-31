/*
	Module processing an event.
*/

var kafkaPusher = require("./../event_processor/kafka_pusher");

exports.processEvent = function (droneEvent) {
	console.log("processing event", droneEvent);
	kafkaPusher.pushEvent(droneEvent);
}