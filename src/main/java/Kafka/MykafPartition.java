package Kafka;


import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/24.
 */
public class MykafPartition implements Partitioner{


    private static Logger LOG= LoggerFactory.getLogger(MykafPartition.class);


    public MykafPartition() {
    }

    public int partition(String topic, Object key, byte[] keybytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numPartions=partitionInfos.size();
        int partitionNum=0;

        try {
            partitionNum=Integer.parseInt((String) key);
        } catch (NumberFormatException e) {
            partitionNum=key.hashCode();
            e.printStackTrace();
        }

        LOG.info("the message sendTo:"+topic+" and the partionNum:"+partitionNum);

        return Math.abs(partitionNum % numPartions);
    }



    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
