package com.kute.kafka;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kute on 2017/2/14.
 *
 * 消费者非线程安全
 * TCP长连接到broker拉取消息
 *
 * 偏移量是该分区中一条消息的唯一标示符
 *
 * http://kafka.apache.org/documentation/#consumerconfigs
 */
public class MsgConsumer {

//    private static Properties props = new Properties();
//
//    static {
//        InputStream input = null;
//
//        try {
//            input = MsgConsumer.class.getResourceAsStream("/consumer.properties");
//            props.load(input);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(null != input) {
//                try {
//                    input.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    public void autocommit() {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String , String > consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList("test", "test2"));
        while (true) {
            consumer.poll(100).forEach(consumerRecord -> {
                System.out.printf("partiton = %d, topic=%s, offset = %d, key = %s, value = %s%n",
                        consumerRecord.partition(),
                        consumerRecord.topic(),
                        consumerRecord.offset(),
                        consumerRecord.key(),
                        consumerRecord.value());
            });
        }

    }


    public static void main(String[] args) throws Exception{
        MsgConsumer producer = new MsgConsumer();

        producer.autocommit();
        System.out.println();

    }
}
