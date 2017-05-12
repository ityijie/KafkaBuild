package Kafka;

/**
 * Created by Administrator on 2017/4/26.
 */
public class test {
    public static void main(String[] args) {
        NewConsumerAutoOffset newConsumerAutoOffset = new NewConsumerAutoOffset();
        newConsumerAutoOffset.createConsumer("ceshi","test1");
        newConsumerAutoOffset.createConsumer("ceshi","test2");
        newConsumerAutoOffset.createConsumer("ceshi","test3");
        newConsumerAutoOffset.createConsumer("ceshi","test4");
        newConsumerAutoOffset.createConsumer("ceshi","test5");

    }
}
