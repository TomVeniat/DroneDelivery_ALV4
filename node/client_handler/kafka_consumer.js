/**
 * Created by Quentin on 10/23/2015.
 */


var kafka = require("kafka-node"),
    Consumer = kafka.Consumer,
    trackingProcessor = require("./../client_handler/tracking_processor"),
    client = new kafka.Client("10.188.64.232:8082");

function followTopic(topicId) {
    var topicFollower = new Consumer(
        client,
        [{topic: topicId}],
        {autocommit: false, fromOffset: true}
    )

    topicFollower.on("message", function(message) {
        trackingProcessor.processTracking(message);
        console.log(message)
    });

    topicFollower.on("error", function(err) {
       console.log(err)
    });


    return topicFollower
}

exports.followTopics = followTopic;
