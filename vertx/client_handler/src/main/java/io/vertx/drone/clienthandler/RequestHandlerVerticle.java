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
            String topic = req.getString("topicId");
            String email = req.getString("email");

            Runnable consumerRunnable = () -> {
                ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                        createConsumerConfig(config()));
                Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
                topicCountMap.put(topic, 1);
                Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
                List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
                //KafkaStream<byte[], byte[]> messageAndMetadatas = streams.get(0);
                streams.forEach(messageAndMetadatas -> {
                        messageAndMetadatas.forEach(msg -> {
                            System.out.println(new String(msg.message()));
                            String message =new String(msg.message());
                            Observer observer = new EmailObserver(email);
                            observer.notify(message);
                        });
                });
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
   