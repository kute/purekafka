package com.kute.kafka.test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kute.kafka.consumer.InfraKafkaConsumer;
import com.kute.kafka.consumer.InfraKafkaConsumerRunner;
import com.kute.kafka.dto.Book;
import com.kute.kafka.producer.InfraKafkaProducer;
import com.kute.kafka.util.KafkaConstants;
import com.kute.kafka.util.OperationUtils;
import com.kute.kafka.util.PropertiesLoader;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.common.Node;

import java.util.List;
import java.util.Properties;

/**
 * Created by kute on 2017/4/8.
 */
public class Test {

    public static void main(String[] args) {

        Test test = new Test();

//        InfraKafkaConsumer consumer = InfraKafkaConsumer.getInstance();
//        System.out.println(consumer.listAllTopics());

//        System.out.println(OperationUtils.getConsumerConfigNames());

//        test.example_1();
        test.example_2();
    }

    public void example_1() {
        String topic = KafkaConstants.KAFKA_TOPIC;

        startProducer(topic);

        startConsumer(topic);

    }

    public void example_2() {
        String topic = KafkaConstants.KAFKA_TOPIC;

        startProducer(topic);

        InfraKafkaConsumerRunner runner = new InfraKafkaConsumerRunner(InfraKafkaConsumer.getInstance(), topic);
        runner.run();
    }

    private void startProducer(String ... topics) {
        String topic = KafkaConstants.KAFKA_TOPIC;

        System.out.println("start producer and begin send msg.....");
        List<String > msgList = Lists.newArrayList();
        for (Integer i = 0; i < 10; i++) {
            Book book = new Book(String.valueOf(i), "content " + i, "author_" + i, i + "234.54");
            String msg = JSONObject.toJSONString(book);
            msgList.add(msg);
        }
        Long start = System.currentTimeMillis();
        try {
            InfraKafkaProducer.getInstance().sendMessage(topic, KafkaConstants.KAFKA_PARTITION_ID,
                    KafkaConstants.KAFKA_TOPIC_KEY, msgList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private void startConsumer(String ... topics) {
        System.out.println("start consumer and wait to consume.....");
        InfraKafkaConsumer.getInstance().consume(topics);
    }

}
