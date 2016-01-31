package Topologie;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
/**
 * Created by jinhong on 31/01/2016.
 */
public class GetTopicBolt extends BaseBasicBolt {
    public void execute(Tuple input, BasicOutputCollector collector) {

        String word = (String) input.getValue(0);
        String id = word.split(",")[0];
        String topic = KafkaTopologie.TOPIC_TABLE.get(id);
        String out1 = "########### id:" + id +  "! ############";
        String out2 = "########### Topic:" + topic +  "! ############";
        System.out.println("out=" + out1);
        System.out.println("out=" + out2);
        collector.emit(new Values(topic));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}