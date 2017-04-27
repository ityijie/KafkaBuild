package Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/24.
 * 自动提交
 */
public class NewConsumerAutoOffset {

    private static Logger LOG= LoggerFactory.getLogger(NewConsumerAutoOffset.class);

    private static Properties properties =new Properties();

    private static KafkaConsumer consumer;

    static {
        //brokerServer(kafka)ip地址,不需要把所有集群中的地址都写上，可是一个或一部分
        properties.put("bootstrap.servers","datanode1:9092,datanode2:9092,datanode3:9092");
        //设置自动提交偏移量(offset),由auto.commit.interval.ms控制提交频率
        properties.put("enable.auto.commit", "true");
        //偏移量(offset)提交频率
        properties.put("auto.commit.interval.ms", "1000");
        //如果采用latest，消费者只能得道其启动后，生产者生产的消息
        properties.put("auto.offset.reset", "earliest");
        //设置心跳时间
        properties.put("session.timeout.ms", "30000");

        //设置key以及value的解析（反序列）类
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");


    }

    public static KafkaConsumer createConsumer(String group_id){

        //设置consumer group name,必须设置
        properties.put("group.id",group_id);

        consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Arrays.asList("flumeTest"));

        while (true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record:records){

                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                System.out.println();
            }

        }


    }




}
