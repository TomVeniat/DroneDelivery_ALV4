/*
	Module responsible for handling client requests.
*/

var express = require("express"),
    kafkaConsumer = require("./kafka_consumer")
    router = express.Router();

router.post('/subscription/create', function (req, res) {
    var message = req.body;
    console.log(message);
    kafkaConsumer.followTopics(message.topicId);
    console.log(message.topicId);
    res.end();
});

router.get('/test', function (req, res) {
    console.log("got smtg");
    res.writeHead(200, {"Content-Type": "text/plain"});
    res.end("Hello Team ! <3\n");
});

module.exports = router;
