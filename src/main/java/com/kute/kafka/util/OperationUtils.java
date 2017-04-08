package com.kute.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

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

 }
