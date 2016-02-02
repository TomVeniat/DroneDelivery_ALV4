package quentinSandbox;


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
import kafka.utils.ZKStringSerializer$;
import org.I0Itec.zkclient.ZkClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Properties;

/**
 * Created by jinhong on 29/01/2016.
 */
public class MyBolt extends BaseBasicBolt{
    public void execute(Tuple input, BasicOutputCollector collector) {
        System.out.println("####################################################################################################");
        System.out.println("------------------------------------ Message Reveiced -----------------------------------------------");
        System.out.println("####################################################################################################");
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("kafka.broker.list", "localhost:9092");
        props.put("kafka.zookeeper.url", "localhost:2181");
        props.put("metadata.broker.list", "localhost:9092");
        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        String word = (String) input.getValue(0);
        String out = "########### I'm " + word +  "! ############";
        String topic = "failedTopic";
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(word);
            topic = "topic" + jsonObject.get("id");
            System.out.println("########################################################################################" +
                    "id : " + jsonObject.get("id")
                    + "################################################################################################");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, ZKStringSerializer$.MODULE$);
        if(!AdminUtils.topicExists(zkClient, topic)) {
            System.out.println("########################################################################################" +
                    "No topic" + topic +
            "  #################################################################################################################");
            Properties properties = new Properties();
            AdminUtils.createTopic(zkClient, topic, 1, 1, properties);
        }


        producer.send(new KeyedMessage<String, String>("outputTopic", word.toString()));
        producer.close();
        System.out.println("out=" + out);
        collector.emit(new Values(out));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}
