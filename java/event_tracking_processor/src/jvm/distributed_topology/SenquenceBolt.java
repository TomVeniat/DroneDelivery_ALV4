package distributed_topology;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by jinhong on 29/01/2016.
 */
public class SenquenceBolt extends BaseBasicBolt{
    public void execute(Tuple input, BasicOutputCollector collector) {

        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("kafka.broker.list", "localhost:9092");
        props.put("kafka.zookeeper.url", "localhost:2181");
        props.put("metadata.broker.list", "localhost:9092");
        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        String word = (String) input.getValue(0);
        String out = "########### I'm " + word +  "! ############";
        producer.send(new KeyedMessage<String, String>("Topic4", word.toString()));
        producer.close();
        System.out.println("out=" + out);
        collector.emit(new Values(out));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}
