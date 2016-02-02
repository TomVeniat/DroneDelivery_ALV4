package quentinSandbox;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import storm.kafka.bolt.KafkaBolt;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by quentin on 01/02/16.
 */
public class KafkaTopology {
    public static void main(String[] args) throws Exception {
        System.out.println("###########################################################################################################################################");
        BrokerHosts brokerHosts = new ZkHosts("localhost:2181");
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "inputTopic", "/zkkafkaspout" , "kafkaspout");
        Config conf = new Config();
        Map<String, String> map = new HashMap<>();
        map.put("metadata.broker.list", "localhost:9092");
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        conf.put("topic", "topic2");
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig));
        builder.setBolt("bolt", new MyBolt()).shuffleGrouping("spout");
        builder.setBolt("kafkabolt", new KafkaBolt<String, Integer>()).shuffleGrouping("bolt");

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        }else{
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Topo", conf, builder.createTopology());
            System.out.println("###########################################################################################################################################");
            Utils.sleep(10000);
            System.out.println("###########################################################################################################################################");
            cluster.killTopology("Topo");
            cluster.shutdown();
        }
    }
}
