package io.vertx.drone.clienthandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RequestHandlerVerticle extends AbstractVerticle {

    public static final String ZOOKEEPER_CONNECT = "zookeeper.connect";
    public static final String GROUP_ID = "group.id";
    public static final String ZOOKEEPER_SESSION_TIMEOUT_MS = "zookeeper.session.timeout.ms";
    public static final String ZOOKEEPER_SYNC_TIME_MS = "zookeeper.sync.time.ms";
    public static final String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
    public static final String TOPIC = "topic";
    public static final String ADDRESS = "address";
    public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
    private Thread consumerThread;


    private ConsumerConfig createConsumerConfig(JsonObject config) {
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "test");
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "smallest");
        return new ConsumerConfig(props);
    }


      @Override
    public void stop() throws Exception {
        consumerThread.interrupt();
        consumerThread.join();
    }

    @Override
        /*
        * The start method is called when the verticle is deployed
        **/
    public void start(Future<Void> fut) {
        HttpServer server = vertx.createHttpServer();
        //Create a router
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/subscription/create").handler(routingContext -> {
            //Get the messages send by the drone
            JsonObject req = routingContext.getBodyAsJson();
            System.out.println(req);
            String topic = req.getString("topicId");
            //MyKafkaConsumer kafkaConsumer = new MyKafkaConsumer(topic);
            //kafkaConsumer.runConsumer();
            String targetAddress = config().getString(ADDRESS, "kafka-" + topic);
            System.out.println("Hello1");
            System.out.println("targetAddress : " + targetAddress);
            Context ctx = vertx.getOrCreateContext();
            System.out.println("Hello2");

            Runnable consumerRunnable = () -> {
                ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                        createConsumerConfig(config()));
                Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
                topicCountMap.put(topic, 1);
                Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
                List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
                KafkaStream<byte[], byte[]> messageAndMetadatas = streams.get(0);
                messageAndMetadatas.forEach(msg -> {
                    ctx.runOnContext(exe -> vertx.eventBus().send(targetAddress, msg.message()));
                    System.out.println(new String(msg.message()));
                });
                System.out.println("Hello2");
                consumer.shutdown();
            };

            consumerThread = new Thread(consumerRunnable);
            consumerThread.start();


            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            response.end();
        });

        router.get("/test").handler(routingContext -> {
            System.out.println("got smtg");
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(200);
            response.putHeader("content-type", "application/json");
            JsonObject obj = new JsonObject();
            obj.put("message", "Hello Team from request_handler!");
            response.end(obj.toString());
        });
        server.requestHandler(router::accept).listen(9001, result -> {
            if (result.succeeded()) {
                fut.complete();
            } else {
                fut.fail(result.cause());
            }
        });
    }
}
   