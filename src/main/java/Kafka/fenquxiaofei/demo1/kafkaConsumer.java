package Kafka.fenquxiaofei.demo1;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/4/28.
 */
public class kafkaConsumer implements Runnable {

    private ConsumerConfig consumerConfig;
    private static String topic="lijietest";
    Properties props;
    final int a_numThreads = 4;

    public kafkaConsumer() {
        props = new Properties();
        props.put("zookeeper.connect", "172.20.1.105:2181");
        //      props.put("zookeeper.connect", "localhost:2181");
        //        props.put("zookeeper.connectiontimeout.ms", "30000");
        props.put("group.id", "blog");
        props.put("zookeeper.session.timeout.ms", "30000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        consumerConfig = new ConsumerConfig(props);
    }

    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        ExecutorService executor = Executors.newFixedThreadPool(a_numThreads);
        for (final KafkaStream stream : streams) {
            executor.submit(new KafkaConsumerThread(stream));
        }

    }


    public static void main(String[] args) {
        System.out.println(topic);
        Thread t = new Thread(new kafkaConsumer());
        t.start();
    }
}
