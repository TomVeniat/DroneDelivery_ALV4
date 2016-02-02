package io.vertx.drone.messagehandler;

import kafka.admin.AdminUtils;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;

/**
 * Created by SUN on 17/01/2016.
 */
public class KafkaPusher {
    final static String TOPIC = "inputTopic";
    private int partitions = 1;
    private int replication = 1;
    private static Properties properties;
    static {
        properties = new Properties();
        // properties.load(new FileInputStream("kafka.properties"));
        //roperties.put("metadata.broker.list", "broker1:9092,broker2:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("kafka.broker.list", "localhost:9092");
        properties.put("kafka.zookeeper.url", "localhost:2181");
        properties.put("metadata.broker.list", "localhost:9092");
    }

    /***
     * Creates a Kafka topic with the given properties.
     * @throws IOException in case the properties file is not in the resources directory.
     */
    public void createTopic() throws IOException {

        String zookeeperUrl = properties.getProperty("kafka.zookeeper.url");

        int sessionTimeoutMs = 10000;
        int connectionTimeoutMs = 10000;
        ZkClient zkClient = new ZkClient(zookeeperUrl, sessionTimeoutMs, connectionTimeoutMs, new ZkSerializer() {
            @Override
            public byte[] serialize(Object data) throws ZkMarshallingError {
                return String.valueOf(data).getBytes();
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                if(bytes == null){
                    return null;
                } else{
                    return new String(bytes, StandardCharsets.UTF_8);
                }
            }
        });

        Properties topicConfig = new Properties();
        AdminUtils.createTopic(zkClient, TOPIC, this.partitions, this.replication, topicConfig);
    }

    public void  pushMessage(Drone drone) {
        long events = 5;
        Random rnd = new Random();
        ProducerConfig producerConfig = new ProducerConfig(properties);
        kafka.javaapi.producer.Producer<String,String> producer = new kafka.javaapi.producer.Producer<String, String>(producerConfig);
        SimpleDateFormat sdf = new SimpleDateFormat();
        System.out.println("test" + drone.toString());
        System.out.println("topic" + TOPIC);
        KeyedMessage<String, String> message =new KeyedMessage<String, String>(TOPIC,drone.toString());
        producer.send(message);
        producer.close();

    }
}
