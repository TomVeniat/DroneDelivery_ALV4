/*
	Module responsible for pushing messages to the Kafka broker.
*/

var kafka = require("kafka-node"),
	Producer = kafka.Producer,
	//ZOOKEEPER_ADDRESS = "10.188.64.232:8082",
	ZOOKEEPER_ADDRESS = "localhost:2181",
	client = new kafka.Client(ZOOKEEPER_ADDRESS),
	producer = new Producer(client),
	DRONE_MESSAGES_TOPIC = "drone_messages";

producer.on("ready", function () {
	console.log("creating topic", DRONE_MESSAGES_TOPIC);
	producer.createTopics([DRONE_MESSAGES_TOPIC], false, function (err, data) {
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
		{topic: DRONE_MESSAGES_TOPIC, messages: message}
	];

	producer.send(payloads, function (err, data) {
		if (err) console.log("pushMessage:", err);
		else if (data) console.log("pushMessage:", data);
	});
}

exports.pushMessage = pushMessage;