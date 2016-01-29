package topologies;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.*;

import java.util.UUID;

/**
 * Created by quentin on 23/01/16.
 */
public class YoloTest {


    public static void main( String[] args ) throws AlreadyAliveException, InvalidTopologyException
    {
        String zkConnString = "localhost:2181";
        String topicName = "7";


        BrokerHosts hosts = new ZkHosts(zkConnString);



        SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/" + topicName, UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);






   //     ZkHosts zkHosts=new ZkHosts("37.187.126.101:8282");

     //   String topic_name="test-topic";
       // String consumer_group_id="id7";
       // String zookeeper_root="";
       // SpoutConfig kafkaConfig=new SpoutConfig(zkHosts, topic_name, zookeeper_root, consumer_group_id);

     //   kafkaConfig.scheme=new SchemeAsMultiScheme(new StringScheme());
      //  kafkaConfig.forceFromStart=true;

        TopologyBuilder builder=new TopologyBuilder();
        int k = 1;
        Integer intObj = new Integer(k);
        Number numObj = (Number) intObj;
        builder.setSpout("KafkaSpout", kafkaSpout,numObj);
        builder.setBolt("PrinterBolt", new PrinterBolt()).globalGrouping("KafkaSpout");

        Config config=new Config();

        LocalCluster cluster=new LocalCluster();

        cluster.submitTopology("KafkaConsumerTopology", config, builder.createTopology());

        try{
            Thread.sleep(60000);
        }catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }

        cluster.killTopology("KafkaConsumerTopology"); // surement a enlever
        cluster.shutdown();
    }
}
