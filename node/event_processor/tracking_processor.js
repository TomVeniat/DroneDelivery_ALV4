var eventProcessor = require("./event_processor");

exports.processTracking = function (trackingMessage) {
	var producedEvent = trackingMessage;

	// TODO map to delivery
	console.log("processing tracking", trackingMessage);
	producedEvent.event = "usual_tracking_message";
	producedEvent.deliveryNumber = "0"; // LOL
	eventProcessor.processEvent(producedEvent);
}