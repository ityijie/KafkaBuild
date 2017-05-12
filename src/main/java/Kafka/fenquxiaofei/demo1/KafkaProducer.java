package Kafka.fenquxiaofei.demo1;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/28.
 */
public class KafkaProducer implements Runnable {
    private Producer<String, String> producer = null;

    private ProducerConfig config = null;


    public KafkaProducer() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "172.20.1.104:2181");

        //      props.put("zookeeper.connect", "localhost:2181");

        // 指定序列化处理类，默认为kafka.serializer.DefaultEncoder,即byte[]
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        // 同步还是异步，默认2表同步，1表异步。异步可以提高发送吞吐量，但是也可能导致丢失未发送过去的消息
        //props.put("producer.type", "sync");

        // 是否压缩，默认0表示不压缩，1表示用gzip压缩，2表示用snappy压缩。压缩后消息中会有头来指明消息压缩类型，故在消费者端消息解压是透明的无需指定。
       // props.put("compression.codec", "1");

        // 指定kafka节点列表，用于获取metadata(元数据)，不必全部指定
        props.put("metadata.broker.list", "172.20.1.104:9092,172.20.1.103:9092");

        config = new ProducerConfig(props);
    }

    @Override
    public void run() {
        producer = new Producer<String, String>(config);
        //      for(int i=0; i<10; i++) {
        //          String sLine = "I'm number " + i;
        //          KeyedMessage<String, String> msg = new KeyedMessage<String, String>("group1", sLine);
        //          producer.send(msg);
        //      }
        for(int i = 1; i <= 4; i++){ //往4个分区发数据
            List<KeyedMessage<String, String>> messageList = new ArrayList<KeyedMessage<String, String>>();
            for(int j = 0; j < 6; j++){ //每个分区6条讯息
                messageList.add(new KeyedMessage<String, String>
                        //String topic, String partition, String message
                        ("lijietest", "partition[" + i + "]", "message[The " + i + " message]"));
            }
            producer.send(messageList);
        }

    }

    public static void main(String[] args) {
        Thread t = new Thread(new KafkaProducer());
        t.start();
    }
}
