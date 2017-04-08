package com.kute.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by kute on 2017/4/8.
 */
public class InfraConsumerInterceptor implements ConsumerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(InfraConsumerInterceptor.class);

    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        return consumerRecords;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map map) {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
