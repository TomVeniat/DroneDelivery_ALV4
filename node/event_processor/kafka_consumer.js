/*
    Module responsible for consuming raw drone messages from the MOM.
*/

var kafka = require("kafka-node"),
    Consumer = kafka.Consumer,
    trackingProcessor = require("./tracking_processor"),
    //ZOOKEEPER_ADDRESS = "10.188.64.232:8082",
    ZOOKEEPER_ADDRESS = "localhost:2181",
    client = new kafka.Client(ZOOKEEPER_ADDRESS),
    DRONE_MESSAGES_TOPIC = "drone_messages",
    consumer = new Consumer(
        client,
        [{topic: DRONE_MESSAGES_TOPIC, offset: 13}],
        {autocommit: false, fromOffset: true}
    );

consumer.on("message", function(message) {
    console.log(message)
    // TODO check message validity here too ?
    trackingProcessor.processTracking(JSON.parse(message.value));
});

consumer.on("error", function(err) {
   console.log(err)
});