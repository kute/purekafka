package com.kute.kafka.util;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.admin.RackAwareMode$;
import kafka.admin.TopicCommand;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.security.JaasUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kute on 2017/4/8.
 */
public class OperationUtils {

    /**
     * consumer config names
     * @return
     */
    public static Set<String > getConsumerConfigNames() {
        return ConsumerConfig.configNames();
    }

    /**
     * producer config names
     * @return
     */
    public static Set<String > getProducerConfigNames() {
        return ProducerConfig.configNames();
    }


    public static void createTopic() {
        String[] options = new String[]{
                "--create",
                "--zookeeper",
                "localhost:2181",
                "--partitions",
                "1",
                "--topic",
                "test1",
                "--replication-factor",
                "1"
        };
        TopicCommand.main(options);
//        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
//        AdminUtils.createTopic(zkUtils, "test", 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
//        zkUtils.close();
    }

    public static void main(String[] args) {
        createTopic();
    }


 }
