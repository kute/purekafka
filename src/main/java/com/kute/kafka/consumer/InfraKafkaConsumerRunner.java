package com.kute.kafka.consumer;

import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kute on 2017/4/8.
 */
public class InfraKafkaConsumerRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(InfraKafkaConsumerRunner.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);

    private InfraKafkaConsumer infraKafkaConsumer;

    private String[] topics;

    public InfraKafkaConsumerRunner(InfraKafkaConsumer infraKafkaConsumer, String ... topics) {
        this.infraKafkaConsumer = infraKafkaConsumer;
        this.topics = topics;
    }

    @Override
    public void run() {

        try {
            if(!closed.get()) {
                infraKafkaConsumer.consume(this.topics);
            }
        } catch (WakeupException e) {
            if(!closed.get()) {
                throw e;
            }
        }
    }

    public void shutdown() {
        closed.set(true);
        infraKafkaConsumer.wakeup();
    }

}
