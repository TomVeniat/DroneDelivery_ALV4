/*
    Module responsible for subscribing to Kafka topics.
*/

var kafka = require("kafka-node"),
    Consumer = kafka.Consumer,
    //ZOOKEEPER_ADDRESS = "10.188.64.232:8082",
    ZOOKEEPER_ADDRESS = "localhost:2181",
    client = new kafka.Client(ZOOKEEPER_ADDRESS);

function followTopic(topicId) {
    var topicFollower = new Consumer(
        client,
        [{topic: topicId}],
        {autocommit: false, fromOffset: true}
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
