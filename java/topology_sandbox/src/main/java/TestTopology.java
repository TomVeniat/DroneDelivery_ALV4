import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.kafka.*;

import java.util.UUID;

/**
 * @author Marc Karassev
 */
public class TestTopology {

    public static void main(String[] args) {
        System.out.println("#####################################################################################################");
        BrokerHosts hosts = new ZkHosts("127.0.0.1:2181");
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test", "/" + "test", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

//        KafkaBolt kafkaBolt= new KafkaBolt()
//                .withTopicSelector(new DefaultTopicSelector("out"))
//                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper());
        System.out.println("#####################################################################################################");

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("kafka spout", kafkaSpout);
        //builder.setSpout("kafka spout", new KafkaSpout());
        builder.setBolt("bolt", new TestBolt())
                .shuffleGrouping("kafka spout");
//        builder.setBolt("kafka bolt", kafkaBolt)
//                .shuffleGrouping("kafka spout");
        System.out.println("#####################################################################################################");

        Config config = new Config();
        config.setDebug(false);
        //Properties props = new Properties();
//        props.put("metadata.broker.list", "localhost:2181");
//        props.put("request.required.acks", "1");
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        props.put("topic", "out");
//        config.put(KafkaBolt.KAFKA_BROKER_PROPERTIES, props);
        System.out.println("#####################################################################################################");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", config, builder.createTopology());
        System.out.println("#####################################################################################################");

        Utils.sleep(2000000000);
        System.out.println("#####################################################################################################");
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
