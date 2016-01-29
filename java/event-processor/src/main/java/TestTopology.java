import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.kafka.*;
import storm.kafka.bolt.KafkaBolt;
import storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.bolt.selector.DefaultTopicSelector;

import java.util.Properties;
import java.util.UUID;

/**
 * @author Marc Karassev
 */
public class TestTopology {

    public static void main(String[] args) {
        BrokerHosts hosts = new ZkHosts("127.0.0.1:2181");
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test", "/" + "test", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

//        KafkaBolt kafkaBolt= new KafkaBolt()
//                .withTopicSelector(new DefaultTopicSelector("out"))
//                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper());

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("kafka spout", kafkaSpout);
        //builder.setSpout("kafka spout", new KafkaSpout());
        builder.setBolt("bolt", new TestBolt())
                .shuffleGrouping("kafka spout");
//        builder.setBolt("kafka bolt", kafkaBolt)
//                .shuffleGrouping("kafka spout");

        Config config = new Config();
        config.setDebug(true);
        Properties props = new Properties();
//        props.put("metadata.broker.list", "localhost:2181");
//        props.put("request.required.acks", "1");
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        props.put("topic", "out");
//        config.put(KafkaBolt.KAFKA_BROKER_PROPERTIES, props);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", config, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
