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
public class GetMessageBolt extends BaseBasicBolt {
    public void execute(Tuple input, BasicOutputCollector collector) {
        String word = (String) input.getValue(0);
        String message[] = word.split(",");
        String topic;
        if(message.length>1){
            topic = KafkaTopologie.TOPIC_TABLE.get(message[0].split(": ")[1]);
        }else{
            topic = "invalid message";
        }
        word += ",topic: "+ topic;
        String out = "########### Message:" + word +  "! ############";
        String out2 = "########### Topic:" + topic +  "! ############";
        System.out.println("out=" + out);
        System.out.println("out=" + out2);
        collector.emit(new Values(word));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}