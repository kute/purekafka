package com.kute.kafka.consumer;

import com.google.common.collect.Lists;
import com.kute.kafka.util.KafkaConstants;
import com.kute.kafka.util.PropertiesLoader;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.errors.InvalidConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
public class InfraKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InfraKafkaConsumer.class);

    private static InfraKafkaConsumer instance = new InfraKafkaConsumer();

    private Consumer<String, String> consumer;

    public InfraKafkaConsumer() {
        logger.info("begin load kafka consumer config...");

        // read configure
        Properties props = PropertiesLoader.loadPropertyFile(KafkaConstants.CONSUMER_CONFIG_FILE);

        //初始化consumer
        try {
            consumer = new KafkaConsumer<>(props);
        } catch (InvalidConfigurationException e) {
            logger.error("KafkaException:The given config parameter has invalid values.", e);
        } catch (Exception e) {
            logger.error("KafkaException: Init consumer failed.", e);
        }
        logger.info("load kafka consumer config success.");
    }

    /**
     * 列出所有的topic
     * @return
     *
     * for example(a topic named 'test' with two partitions):
     *
     *  {
     *     test=[
     *            Partition(topic = test, partition = 1, leader = 0, replicas = [0,], isr = [0,]),
     *            Partition(topic = test, partition = 0, leader = 0, replicas = [0,], isr = [0,])
     *          ],
     *     test1=[
     *            Partition(topic = test1, partition = 0, leader = 0, replicas = [0,], isr = [0,])
     *           ]
     *   }
     *
     */
    public Map<String, List<PartitionInfo>> listAllTopics() {
        return consumer.listTopics();
    }

    /**
     * 列出所有的topic name
     * @return
     *
     *   for example:
     *      [test, test1]
     */
    public Set<String > listAllTopicsName() {
        return this.listAllTopics().keySet();
    }

    public void consume(String ... topics) {

        long start = System.currentTimeMillis();
        try {
            consumer.subscribe(Lists.newArrayList(topics));
            while(true) {
                consumer.poll(100).forEach(consumerRecord -> {
                    System.out.printf("partiton = %d, topic=%s, offset = %d, key = %s, value = %s%n",
                            consumerRecord.partition(),
                            consumerRecord.topic(),
                            consumerRecord.offset(),
                            consumerRecord.key(),
                            consumerRecord.value());
                });
                if(System.currentTimeMillis() - start > 10000) {
                    break;
                }
            }
        } finally {
            try {
                consumer.close();
            } catch (Exception e) {}
        }
    }

    public void close() {
        try {
            consumer.close();
        } catch (Exception e) {}
    }

    public void wakeup() {
        consumer.wakeup();
    }

    public static InfraKafkaConsumer getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        InfraKafkaConsumer consumer = InfraKafkaConsumer.getInstance();
        consumer.consume("test");
    }
}
