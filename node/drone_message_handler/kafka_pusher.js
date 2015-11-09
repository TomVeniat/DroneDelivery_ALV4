/*
	Module responsible for pushing messages to the Kafka broker.
*/

var kafka = require("kafka-node"),
	Producer = kafka.Producer,
	kafkaProperties = require("../properties"),
	client = new kafka.Client(kafkaProperties.ZOOKEEPER_ADDRESS),
	producer = new Producer(client)

producer.on("ready", function () {
	console.log("creating topic", kafkaProperties.RAW_DRONE_MESSAGES_TOPIC);
	producer.createTopics([kafkaProperties.RAW_DRONE_MESSAGES_TOPIC], false, function (err, data) {
		if (err) console.log("onReady:", err);
		else {
			if (data) console.log("onReady:", data);
			console.log("kafka-producer operational");
		}
	});
});

producer.on("error", function(err) {
	console.log("onError:", err);
});

/*
	Message must be a string.
*/
function pushMessage(message) {
	var payloads = [
		{topic: kafkaProperties.RAW_DRONE_MESSAGES_TOPIC, messages: message}
	];

	producer.send(payloads, function (err, data) {
		if (err) console.log("pushMessage:", err);
		else if (data) console.log("pushMessage:", data);
	});
}

exports.pushMessage = pushMessage;