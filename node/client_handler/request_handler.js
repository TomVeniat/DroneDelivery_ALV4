/*
	Module responsible for handling client requests.
*/

var express = require("express"),
    kafkaConsumer = require("./kafka_consumer")
    router = express.Router();
    consumers = [];

router.post('/subscription/create', function (req, res) {
    var message = req.body;
    console.log(message);
    consumers.push(kafkaConsumer.followTopics(message.topicId));
    res.end();
});

router.get('/test', function (req, res) {
    console.log("got smtg");
    res.writeHead(200, {"Content-Type": "text/plain"});
    res.end("Hello Team from request_handler! <3\n");
});

module.exports = router;
