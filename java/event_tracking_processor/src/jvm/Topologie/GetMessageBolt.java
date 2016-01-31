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
        System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAANS GEEEEEEEEEEEEET MEEEEEEEESSSSSSSSSSAAAAAGE");
        String word = (String) input.getValue(0);
        String message[] = word.split(",");
        String output;
        if(message.length>1){
            output = message[1];
        }else{
            output = "invalid messgae";
        }
        String out = "########### Message:" + output +  "! ############";
        System.out.println("out=" + out);
        collector.emit(new Values(output));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields("message"));
    }
}