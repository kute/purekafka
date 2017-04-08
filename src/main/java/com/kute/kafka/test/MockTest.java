package com.kute.kafka.test;

import com.google.common.collect.Lists;
import com.kute.kafka.consumer.InfraKafkaConsumer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kute on 2017/4/8.
 */
public class MockTest {


    public static void main(String[] args) {

        String topic = "test";
//        Node[] nodes = new Node[]{new Node(1, "localhost", 2181)};
//        List<PartitionInfo> partitionInfoList = Lists.newArrayList(new PartitionInfo(
//                topic, 0, nodes[0], nodes, nodes
//        ));
//        Cluster cluster = new Cluster("1", Arrays.asList(nodes), partitionInfoList, null, null);

        boolean autoComplete = true;

        System.out.println("start consumer and wait to consume.....");
        InfraKafkaConsumer.getInstance().consume(topic);
//        Producer<String , String > producer = new MockProducer<>(autoComplete, new StringSerializer(), new StringSerializer());
//        producer.send(new ProducerRecord<String, String>(topic, 0, "test-key", "mock-value"));




    }

}
