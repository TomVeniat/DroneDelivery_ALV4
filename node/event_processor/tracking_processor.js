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
	producedEvent.deliveryNumber = "" + trackingMessage.id; // Get delivery number of the database with the trackingMessage.id
	console.log(producedEvent.deliveryNumber);
	console.log("coucou");
	eventProcessor.processEvent(producedEvent);
}