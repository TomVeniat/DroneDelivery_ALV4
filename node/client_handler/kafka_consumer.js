/*
    Module responsible for subscribing to Kafka topics.
*/

var kafka = require("kafka-node"),
    Consumer = kafka.Consumer,
    kafkaProperties = require("../properties"),
    client = new kafka.Client(kafkaProperties.ZOOKEEPER_ADDRESS);

function followTopic(topicId) {
    var topicFollower = new Consumer(
        client,
        [{topic: topicId}],
        {autocommit: false, fromOffset: true}//false, fetchMaxWaitMs:}
    )

    topicFollower.on("message", function(message) {
        // TODO maintenir une liste des followers et leur envoyer une requête http à chaque fois qu'il y a un message ?
        console.log(message)
    });

    topicFollower.on("error", function(err) {
       console.log(err)
    });

    return topicFollower
}

exports.followTopics = followTopic;
