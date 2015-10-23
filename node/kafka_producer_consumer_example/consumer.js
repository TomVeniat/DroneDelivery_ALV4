/**
	@author Marc Karassev

	Script creating a kafka consumer consuming all messages from the beginning from 2 topics during 5 seconds before terminating.
*/

console.log("requires a kafka server running with default configuration");

var kafka = require("kafka-node"),
	Consumer = kafka.Consumer,
	client = new kafka.Client("37.187.126.101:2181"),
	marc = new Consumer(
		client,
		[{topic: "expose"}, {topic: "projet"}],
		{autocommit: false, fromOffset: true}
	);

marc.on('message', function (message) {
	console.log(message);
});

marc.on("error", function (err) {
	console.log(err);
});

setTimeout(function () {
	marc.close();
}, 5000);