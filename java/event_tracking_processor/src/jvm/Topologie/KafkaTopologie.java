package Topologie;

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
 * Created by jinhong on 29/01/2016.
 */
public class KafkaTopologie {
    public static Map<String, String> TOPIC_TABLE = new HashMap<>();

    public static void main(String[] args) throws Exception {
        for(int i =0;i<100;i++){
            TOPIC_TABLE.put(""+i, "Topic"+i);
        }
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
        builder.setBolt("getTopicBolt", new GetTopicBolt()).shuffleGrouping("spout");
       // builder.setBolt("getMessageBolt", new GetMessageBolt()).shuffleGrouping("spout");
        //builder.setBolt("sendMessageBolt", new SendMessageBolt()).shuffleGrouping("getTopicBolt").shuffleGrouping("getMessageBolt");
        builder.setBolt("sendMessageBolt", new SendMessageBolt()).shuffleGrouping("getTopicBolt");
        //builder.setBolt("bolt", new SenquenceBolt()).shuffleGrouping("spout");
        builder.setBolt("kafkabolt", new KafkaBolt<String, Integer>()).shuffleGrouping("getTopicBolt");
        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        }else{
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Topo", conf, builder.createTopology());
            Utils.sleep(10000);
            cluster.killTopology("Topo");
            cluster.shutdown();
        }
    }
}
