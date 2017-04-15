package com.kute.kafka.producer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.kute.kafka.util.KafkaConstants;
import com.kute.kafka.util.PropertiesLoader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.*;
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

    private Producer<String, String> producer;

    private InfraKafkaProducer() {
        // 初始化kafka exception monitor
        logger.info("begin load kafka producer config...");

        // read configure
        Properties props = PropertiesLoader.loadPropertyFile(KafkaConstants.PRODUCER_CONFIG_FILE);

        //初始化producer
        try {
            producer = new KafkaProducer<>(props);
        } catch (InvalidConfigurationException e) {
            logger.error("KafkaException:The given config parameter has invalid values.", e);
        } catch (Exception e) {
            logger.error("KafkaException: Init producer failed.", e);
        }
        logger.info("load kafka producer config success.");
    }

    public static InfraKafkaProducer getInstance() {
        return instance;
    }

    /**
     * send single msg
     * @param topic
     * @param msgData
     * @return
     */
    public Boolean sendMessage(String topic, String msgData) {
        return sendMessage(topic, null, msgData);
    }

    /**
     * send single msg
     * @param topic
     * @param key
     * @param msgData
     * @return
     */
    public Boolean sendMessage(String topic, String key, String msgData) {
        return sendMessage(topic, null, key, msgData);
    }

    /**
     * send single msg
     * @param topic
     * @param partitionId
     * @param key
     * @param msgData
     * @return
     */
    public Boolean sendMessage(String topic, Integer partitionId, String key, String msgData) {
        return sendMessage(topic, partitionId, null, key, msgData);
    }

    /**
     * send single msg
     * @param topic
     * @param partitionId
     * @param timeStamp
     * @param key
     * @param msgData
     * @return
     */
    public Boolean sendMessage(String topic, Integer partitionId, Long timeStamp, String key, String msgData) {
        Boolean result = Boolean.TRUE;
        if(Strings.isNullOrEmpty(msgData)) {
            return result;
        }
        int retry = 0;
        ProducerRecord<String, String > record;
        if(null == key) {
            record = new ProducerRecord<>(topic, msgData);
        } else if(null == partitionId) {
            record = new ProducerRecord<>(topic, key, msgData);
        } else if(null == timeStamp) {
            record = new ProducerRecord<>(topic, partitionId, key, msgData);
        } else {
            record = new ProducerRecord<>(topic, partitionId, timeStamp, key, msgData);
        }
        while(retry++ <= KafkaConstants.MAX_RETRY_TIMES) {
            Future future = producer.send(record, new MsgCallBack(System.currentTimeMillis(), retry, msgData));
            try {
                if(null != future.get(2, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (Exception e) {
                logger.error("msg={} sendTimeout after retry={} and error={}", new Object[]{
                        msgData, retry, retry
                });
                if(retry == KafkaConstants.MAX_RETRY_TIMES) {
                    result = Boolean.FALSE;
                }
            }
        }
        return result;
    }

    /**
     * send mutil msg
     * @param topic
     * @param msgDataList
     */
    public void sendMessage(String topic, List<String> msgDataList) {
        sendMessage(topic, null, msgDataList);
    }

    /**
     * send mutil msg
     * @param topic
     * @param key
     * @param msgDataList
     */
    public void sendMessage(String topic, String key, List<String> msgDataList) {
        sendMessage(topic, null, key, msgDataList);
    }

    /**
     * send mutil msg
     * @param topic
     * @param partitionId
     * @param key
     * @param msgDataList
     */
    public void sendMessage(String topic, Integer partitionId, String key, List<String> msgDataList) {
        sendMessage(topic, partitionId, null, key, msgDataList);
    }

    /**
     * send mutil msg
     * @param topic
     * @param partitionId
     * @param timeStamp
     * @param key
     * @param msgDataList
     */
    public void sendMessage(String topic, Integer partitionId, Long timeStamp, String key, List<String> msgDataList) {
        if(CollectionUtils.isEmpty(msgDataList)) {
            return;
        }
        for(String msgData: msgDataList) {
            sendMessage(topic, partitionId, timeStamp, key, msgData);
        }
    }
    
    class MsgCallBack implements Callback {
        
        private final long sendTime;
        private final int retry;
        private final String msg;

        public MsgCallBack(long sendTime, int retry, String msg) {
            this.sendTime = sendTime;
            this.retry = retry;
            this.msg = msg;
        }

        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if(null == metadata) {
                logger.error("Kafka send msg failed: msg={}, sendTime={}, retry={}, error={}", new Object[]{
                        msg, sendTime, retry, e
                });
            } else {
                logger.info("Kafka send msg ok: msg={}, metaData={}", new Object[]{
                        msg, metadata.toString()
                });
            }
        }
        
    }
}
