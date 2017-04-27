package Kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Created by Administrator on 2017/4/24.
 */

/**
 * 手动提交
 */
public class ManualOffsetConsumer {


    private static Logger LOG= LoggerFactory.getLogger(ManualOffsetConsumer.class);

    private static Properties props =new Properties();

    private static final int batchSize=5;

    static {
        props.put("bootstrap.servers","datanode2:9092");

        //设置consumer group name
        props.put("group.id","manual_g1");
        props.put("enable.auto.commit", "false");

        //设置使用最开始的offset偏移量为该group.id的最早。如果不设置，则会是latest即该topic最新一个消息的offset
        //如果采用latest，消费者只能得道其启动后，生产者生产的消息
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");

        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

    }

    public static void main(String[] args) {

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("producer_test"));


        List<ConsumerRecord<String,String>> buffer=new ArrayList<ConsumerRecord<String, String>>();
        while (true){

            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record:records){

                LOG.info("consumer message values is "+record.value()+" and the offset is "+ record.offset());

                buffer.add(record);
            }
            if (buffer.size()>=batchSize){

                LOG.info("now commit offset");
                consumer.commitAsync();
                buffer.clear();

            }


        }

    }

    public static void manualConmitPartition(){

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("producer_test"));

        while (true){

            ConsumerRecords<String, String> records = consumer.poll(500);
            for (TopicPartition topicPartition:records.partitions()){

                List<ConsumerRecord<String, String>> partRecords = records.records(topicPartition);
                for (ConsumerRecord<String, String> record:partRecords){

                    LOG.info("now consumer the message it's offset is :"+record.offset() + " and the value is :" + record.value());

                }

                long lastoffset = partRecords.get(partRecords.size() - 1).offset();
                LOG.info("now commit the partition[ "+topicPartition.partition()+"] offset");

                //按分区提交
                consumer.commitSync(Collections.singletonMap(topicPartition,new OffsetAndMetadata(lastoffset+1)));

            }

        }






    }





}
