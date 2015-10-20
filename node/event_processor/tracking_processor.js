var eventProcessor = require("./event_processor");

exports.processTracking = function (trackingMessage) {
	var producedEvent = trackingMessage;

	console.log("processing tracking", trackingMessage);
	producedEvent.event = "usual tracking message";
	eventProcessor.processEvent(producedEvent);
}