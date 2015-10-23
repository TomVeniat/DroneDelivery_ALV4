/**
 * Created by Quentin on 10/21/2015.
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


module.exports = router;