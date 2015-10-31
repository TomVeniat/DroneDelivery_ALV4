/*
    Module responsible for consuming raw drone messages from the MOM.
*/

var kafka = require("kafka-node"),
    Consumer = kafka.Consumer,
    trackingProcessor = require("./tracking_processor"),
    kafkaProperties = require("../kafka_properties"),
    client = new kafka.Client(kafkaProperties.ZOOKEEPER_ADDRESS),
    consumer = new Consumer(
        client,
        [{topic: kafkaProperties.RAW_DRONE_MESSAGES_TOPIC, offset: 0}],
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