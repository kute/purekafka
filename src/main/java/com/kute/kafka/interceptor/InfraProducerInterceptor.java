package com.kute.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by kute on 2017/4/8.
 */
public class InfraProducerInterceptor implements ProducerInterceptor {

    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        System.out.println("Msg begin send:" + producerRecord.toString());
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        //has be ensured that msg was send ok
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
