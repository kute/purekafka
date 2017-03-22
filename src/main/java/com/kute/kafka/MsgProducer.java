package com.kute.kafka;

import com.google.common.base.Joiner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by kute on 2017/2/14.
 *
 * 生产者线程安全
 * 同一个区的消息顺序发送
 * send 异步
 * config:  http://kafka.apache.org/documentation/#producerconfigs
 */
public class MsgProducer {

    private static Properties props = new Properties();

    static {
        InputStream input = null;

        try {
            input = MsgProducer.class.getResourceAsStream("/producer.properties");
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != input) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void send() throws Exception{
        Producer<String, String > producer = new KafkaProducer<>(props);

        for(int i=0; i<5; i++) {
            ProducerRecord<String , String > record = new ProducerRecord<>("test", String.valueOf(i), String.valueOf(i) + "=msg6");
            producer.send(record, (metadata, e) -> {
                if(null != metadata) {
                    System.out.println(Joiner.on(",").useForNull("").join(metadata.offset(), metadata.partition(), metadata.topic(),
                            metadata.timestamp()));
                } else {
                    e.printStackTrace();
                }
            });
        }
        producer.close();

        streamTransfer();
    }

    public void streamTransfer() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        StreamsConfig config = new StreamsConfig(props);

        KStreamBuilder builder = new KStreamBuilder();
        builder.stream("test").mapValues(value -> value.toString() + "==test2").to("test2");

        KafkaStreams kafkaStreams = new KafkaStreams(builder, config);
        kafkaStreams.start();
    }


    public static void main(String[] args) throws Exception{
        MsgProducer producer = new MsgProducer();

        producer.send();

    }
}
