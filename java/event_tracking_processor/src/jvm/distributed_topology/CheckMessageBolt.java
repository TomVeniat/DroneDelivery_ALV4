package distributed_topology;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
/**
 * Created by jinhong on 31/01/2016.
 */
public class CheckMessageBolt extends BaseBasicBolt {
    public void execute(Tuple input, BasicOutputCollector collector) {

        String word = (String) input.getValue(0);
        String message[] = word.split(",");
        String lon = message[2].split(": ")[1];
        String topic;
        if(Double.parseDouble(lon)<10) {
            topic = "errorTopic";
            word = word.split(",topic: ")[0] + ",topic: " + topic;
        }
        String out = "########### Message:" + word +  "! ############";
        System.out.println("out=" + out);
        collector.emit(new Values(word));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}