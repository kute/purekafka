package com.kute.kafka.producer;

import java.util.List;
import java.util.Properties;

import com.kute.kafka.util.KafkaConfigLoader;
import com.kute.kafka.util.KafkaConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.common.errors.InvalidConfigurationException;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 
 * @author bl
 *
 */
public class InfraKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(InfraKafkaProducer.class);

    // instance 
    private static InfraKafkaProducer instance = new InfraKafkaProducer();

    /**
     * 默认尝试次数
     */
    private static final int DEFAULT_RETRY_TIMES = 3;

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIMEOUT = 3000;

    private Producer<String, String> producer;

    private InfraKafkaProducer() {
        // 初始化kafka exception monitor
        logger.info("begin load kafka producer config...");
        System.out.println("begin load kafka producer config...");

        // read configure
        Properties props = KafkaConfigLoader.loadPropertyFile(this.getClass().getClassLoader()
                .getResourceAsStream(KafkaConstants.KAFKA_CONFIG_FILE));

        //初始化producer
        try {
            producer = new KafkaProducer<>(props);
        } catch (InvalidConfigurationException e) {
            logger.error("KafkaException:The given config parameter has invalid values.", e);
            System.out.println("KafkaException:The given config parameter has invalid values."+ e);
        } catch (Exception e) {
            logger.error("KafkaException: Init producer failed.", e);
            System.out.println("KafkaException: Init producer failed."+ e);
        }
        logger.info("load kafka producer config success.");
        System.out.println("load kafka producer config success.");
    }

    public static InfraKafkaProducer getInstance() {
        return instance;
    }

    /**
     * 发送一条message
     * 
     * @param topic
     * @param partitionKey
     * @param msgData
     * @return
     */
    public boolean sendMessage(String topic, Integer partitionKey, String key, String msgData) {
        if(Strings.isNullOrEmpty(msgData)) {
            return true;
        }
        List<String> msgList = Lists.newArrayList(msgData);
        return sendMessage(topic, partitionKey, key, msgList);
    }

    /**
     * 发送多条message
     * 
     * @param topic
     * @param partitionKey
     * @param msgDataList
     * @return
     */
    public boolean sendMessage(String topic, Integer partitionKey, String key, List<String> msgDataList) {
        if(CollectionUtils.isEmpty(msgDataList)) {
            return true;
        }
        int size = msgDataList.size();
        long sendTime = System.currentTimeMillis();
        for (int i=0; i<size; i++) {
            String msgData = msgDataList.get(i);
            System.out.println("begin send msg:" + i);
            producer.send(new ProducerRecord<String, String>(topic, partitionKey, key, msgData), new MsgCallBack(sendTime, i, msgData));
        }
        producer.close();
        return false;
    }
    
    class MsgCallBack implements Callback {
        
        private final long sendTime;
        private final int msgId;
        private final String msg;

        public MsgCallBack(long sendTime, int msgId, String msg) {
            this.sendTime = sendTime;
            this.msgId = msgId;
            this.msg = msg;
        }

        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if(null == metadata) {
                //TODO 失败
                System.out.println("send failed:" + e);
            } else {
                //TODO 成功
                System.out.println("send ok:" + metadata.offset());
            }
        }
        
    }
}
