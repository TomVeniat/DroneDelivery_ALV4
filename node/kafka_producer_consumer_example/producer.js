/**
	@author Marc Karassev

	Script creating a kafka producer creating two new topics and then pushing 3 messages to them before terminating.
*/

console.log("requires a local kafka server running with default configuration");

var kafka = require("kafka-node"),
	Producer = kafka.Producer,
	client = new kafka.Client(),
	producer = new Producer(client),
	payloads = [
		{topic: "projet", messages: "faut se mettre au boulot les gars !"},
		{topic: "expose", messages: ["on doit avoir quelque chose pour la semaine prochaine", "go copy/paste la prez de Benni ?"]}
	];

producer.on("ready", function () {
	producer.createTopics(["projet", "expose"], false, function (err, data) {
		if (err) console.log(err);
		if (data) console.log(data);
		producer.send(payloads, function (err, data) {
			if (err) console.log(err);
			if (data) console.log(data);
			client.close();
		});
	});
});

producer.on("error", function(err) {
	console.log(err);
});
