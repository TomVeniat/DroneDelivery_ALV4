/*
	Module responsible for pushing messages to the Kafka broker.
*/

var kafka = require("kafka-node"),
	Producer = kafka.Producer,
	client = new kafka.Client("10.188.64.232:8082"),
	producer = new Producer(client)

producer.on("ready", function () {
	console.log("kafka-producer operational");
});

producer.on("error", function(err) {
	console.log(err);
});

function pushEvent(event) {
	producer.createTopics([event.deliveryNumber], false, function (err, data) {
		if (err) console.log(err);
		else {
			var payloads = [
				{topic: event.deliveryNumber, messages: event.event}
			];

			if (data) console.log(data);
			producer.send(payloads, function (err, data) {
				if (err) console.log(err);
				if (data) console.log(data);
			});
		}
	});
}


exports.pushEvent = pushEvent;