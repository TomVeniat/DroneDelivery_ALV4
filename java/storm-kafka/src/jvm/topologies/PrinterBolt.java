package topologies;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import storm.kafka.bolt.KafkaBolt;

/**
 * Created by quentin on 23/01/16.
 */
public class PrinterBolt extends KafkaBolt {
    private static final long serialVersionUID = 1L;

    public void execute(Tuple input, BasicOutputCollector collector) {
        // TODO Auto-generated method stub
        String word=input.getString(0);
        if(StringUtils.isBlank(word))
        {
            return;
        }

        System.out.println("Word: "+word);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub

    }
}
