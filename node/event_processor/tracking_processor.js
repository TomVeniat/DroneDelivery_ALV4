/*
	Module processing a drone message.
*/

var eventProcessor = require("./event_processor");

exports.processTracking = function (trackingMessage) {
	var producedEvent = trackingMessage;

	// TODO map to delivery
	console.log("processing tracking", trackingMessage);
	if (trackingMessage.event == undefined)
		producedEvent.event = "usual_tracking_message";
	producedEvent.deliveryNumber = "" + trackingMessage.id; // LOL
	eventProcessor.processEvent(producedEvent);
}