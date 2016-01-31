package Topologie;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import kafka.admin.AdminUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.I0Itec.zkclient.ZkClient;
import kafka.utils.ZKStringSerializer$;
import org.json.simple.JSONObject;

import java.util.Properties;

/**
 * Created by jinhong on 31/01/2016.
 */
public class SendMessageBolt extends BaseBasicBolt {
    public void execute(Tuple input, BasicOutputCollector collector) {

        ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, ZKStringSerializer$.MODULE$);

        //JSONObject jsonObject = (JSONObject) input.getValue(1);
        //int droneNumber = (int) jsonObject.get("id");
        //String topicName =  new String(KafkaTopologie.TOPIC_TABLE.get(droneNumber));
        String word = (String) input.getValue(0);
        String topic = word.split(",topic: ")[1];
        if(!AdminUtils.topicExists(zkClient, topic)) {
            System.out.println("No topic" + topic);
            Properties properties = new Properties();
            AdminUtils.createTopic(zkClient, topic, 1, 1, properties);
        }


        //System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAANS SSSSSSSSSSSSSSSSEEEEEEEND MESSSSSSAGEEE");
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("kafka.broker.list", "localhost:9092");
        props.put("kafka.zookeeper.url", "localhost:2181");
        props.put("metadata.broker.list", "localhost:9092");
        ProducerConfig config = new ProducerConfig(props);
        word = word.split(",topic:")[0];
        Producer<String, String> producer = new Producer<String, String>(config);
        String out = "###########" + topic +':'+ word +  " SENT! ############";
        producer.send(new KeyedMessage<String, String>(topic.toString(), word.toString()));
        producer.close();
        System.out.println("out=" + out);
        collector.emit(new Values(out));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}
