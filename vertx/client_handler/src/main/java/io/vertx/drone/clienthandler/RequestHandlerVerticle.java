package io.vertx.drone.clienthandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by SUN on 21/01/2016.
 */
public class RequestHandlerVerticle extends AbstractVerticle {
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
            MyKafkaConsumer kafkaConsumer = new MyKafkaConsumer(topic);
            //kafkaConsumer.runConsumer();
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










/*


    public static final String EVENTBUS_DEFAULT_ADDRESS = "kafka.message.consumer";
    public static final int DEFAULT_POLL_MS = 100;
    private String busAddress;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerVerticle.class);
    private EventBus bus;

    private AtomicBoolean running;
    private KafkaConsumer consumer;
    private List<String> topics;
    private JsonObject verticleConfig;
    private ExecutorService backgroundConsumer;
    private int pollIntervalMs;

    @Override
    public void start(final Future<Void> startedResult) {

        try {
            bus = vertx.eventBus();
            running = new AtomicBoolean(true);

            verticleConfig = new JsonObject();
            verticleConfig.put(ConfigConstants.ZK_CONNECT, "localhost:2181");
            verticleConfig.put(ConfigConstants.BOOTSTRAP_SERVERS, "localhost:9092");
            verticleConfig.put(ConfigConstants.GROUP_ID, "testGroup");
            verticleConfig.put(ConfigConstants.BACKOFF_INCREMENT_MS, "100");
            verticleConfig.put(ConfigConstants.AUTO_OFFSET_RESET, "smallest");
            verticleConfig.put(ConfigConstants.TOPICS, Arrays.asList("javatest2"));
            verticleConfig.put(ConfigConstants.EVENTBUS_ADDRESS, "kafka.to.vertx.bridge");
            verticleConfig.put(ConfigConstants.CONSUMER_POLL_INTERVAL_MS, 1000);

            Properties kafkaConfig = populateKafkaConfig(verticleConfig);
            JsonArray topicConfig = verticleConfig.getJsonArray(ConfigConstants.TOPICS);

            busAddress = verticleConfig.getString(ConfigConstants.EVENTBUS_ADDRESS, EVENTBUS_DEFAULT_ADDRESS);
            pollIntervalMs = verticleConfig.getInteger(ConfigConstants.CONSUMER_POLL_INTERVAL_MS, DEFAULT_POLL_MS);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                // try to disconnect from ZK as gracefully as possible
                public void run() {
                    shutdown();
                }
            });

            backgroundConsumer = Executors.newSingleThreadExecutor();
            backgroundConsumer.submit(() -> {
                try {
                    consumer = new KafkaConsumer(kafkaConfig);

                    topics = new ArrayList<>();
                    for (int i = 0; i < topicConfig.size(); i++) {
                        topics.add(topicConfig.getString(i));
                        System.out.println("Subscribing to topic ");
                    }

                    // signal success before we enter read loop
                    startedResult.complete();
                    consume();
                } catch (Exception ex) {
                    String error = "Failed to startup";
                    System.err.println(error);
                    bus.send(ConfigConstants.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
                    startedResult.fail(ex);
                }
            });
        } catch (Exception ex) {
            String error = "Failed to startup";
            logger.error(error, ex);
            bus.send(ConfigConstants.CONSUMER_ERROR_TOPIC, getErrorString("Failed to startup", ex.getMessage()));
            startedResult.fail(ex);
        }
    }

    private String getErrorString(String error, String errorMessage) {
        return String.format("%s - error: %s", error, errorMessage);
    }

    */
/**
     * Handles looping and consuming
     *//*

    private void consume() {
        consumer.subscribe(topics);
        while (running.get()) {
            try {
                ConsumerRecords records = consumer.poll(pollIntervalMs);

                // there were no messages
                if (records == null) { continue; }

                Iterator<ConsumerRecord<String,String>> iterator = records.iterator();

                // roll through and put each kafka message on the event bus
                while (iterator.hasNext()) { sendMessage(iterator.next()); }

            } catch (Exception ex) {
                String error = "Error consuming messages from kafka";
                logger.error(error, ex);
                bus.send(ConfigConstants.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
            }
        }
    }


    @Override
    public void stop() { running.compareAndSet(true, false); }

    */
/**
     * Send the inbound message to the event bus consumer.
     *
     * @param record the kafka event
     *//*

    private void sendMessage(ConsumerRecord<String, String> record) {
        try {
                JsonObject obj = KafkaEvent.createEventForBus(record);
                bus.send(busAddress, obj);
                System.out.println(obj);
        }
        catch (Exception ex) {
            String error = String.format("Error sending messages on event bus - record: %s", record.toString());
            System.err.println(error);
            bus.send(ConfigConstants.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
        }
    }



    private Properties populateKafkaConfig(JsonObject config) {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConfigConstants.ZK_CONNECT, config.getString(ConfigConstants.ZK_CONNECT, "localhost:2181"));
        consumerConfig.put(ConfigConstants.BACKOFF_INCREMENT_MS,
                config.getString(ConfigConstants.BACKOFF_INCREMENT_MS, "100"));
        consumerConfig.put(ConfigConstants.AUTO_OFFSET_RESET,
                config.getString(ConfigConstants.AUTO_OFFSET_RESET, "smallest"));

        consumerConfig.put(ConfigConstants.BOOTSTRAP_SERVERS, getRequiredConfig(ConfigConstants.BOOTSTRAP_SERVERS));

        consumerConfig.put(ConfigConstants.KEY_DESERIALIZER_CLASS,
                config.getString(ConfigConstants.KEY_DESERIALIZER_CLASS, ConfigConstants.DEFAULT_DESERIALIZER_CLASS));
        consumerConfig.put(ConfigConstants.VALUE_DESERIALIZER_CLASS,
                config.getString(ConfigConstants.VALUE_DESERIALIZER_CLASS, ConfigConstants.DEFAULT_DESERIALIZER_CLASS));
        consumerConfig.put(ConfigConstants.GROUP_ID, getRequiredConfig(ConfigConstants.GROUP_ID));
        return consumerConfig;
    }

    private String getRequiredConfig(String key) {
        String value = verticleConfig.getString(key, null);

        if (null == value) {
            throw new IllegalArgumentException(String.format("Required config value not found key: %s", key));
        }
        return value;
    }

    */
/**
     * Handle stopping the consumer.
     *//*

    private void shutdown() {
        running.compareAndSet(true, false);
        try {
            if(consumer != null) {
                try {
                    consumer.unsubscribe();
                    consumer.close();
                    consumer = null;
                } catch (Exception ex) {  }
            }

            if(backgroundConsumer != null) {
                backgroundConsumer.shutdown();
                backgroundConsumer = null;
            }
        } catch (Exception ex) {
            logger.error("Failed to close consumer", ex);
        }
    }
*/
}
