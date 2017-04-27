package Kafka.atlast;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/4/26.
 * //多线程没有用,已测试
 *
 */
public class MyConsume {
    private static Logger LOG = LoggerFactory.getLogger(MyConsume.class);

    public MyConsume() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.20.1.103:9092,172.20.1.104:9092");
        //设置不自动提交，自己手动更新offset
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset", "latest");
        properties.put("zookeeper.connect", "172.20.1.103:2181,172.20.1.104:2181");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "lijieGroup");
        properties.put("auto.commit.interval.ms", "1000");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //执行消费
        for (int i = 0; i < 7; i++) {
            executor.execute(new ConsumerThreadNew(new KafkaConsumer<String, String>(properties),
                    "flumeTest", "消费者" + (i + 1)));
        }
    }
}
