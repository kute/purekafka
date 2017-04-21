package com.kute.kafka.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.admin.RackAwareMode$;
import kafka.admin.TopicCommand;
import kafka.utils.ZKStringSerializer;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.zookeeper.ZKUtil;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by kute on 2017/4/8.
 */
public class OperationUtils {

    private static final Logger logger = LoggerFactory.getLogger(OperationUtils.class);

    private ZkClient zkClient = null;
    private ZkUtils zkUtils = null;
    private String zkUrl;
    private Integer sessionTimeout;
    private Integer connectionTimeout;

    public OperationUtils(String host, Integer port, Integer sessionTimeout, Integer connectionTimeout) {
        this.zkUrl = getUrl(host, port);
        this.sessionTimeout = sessionTimeout;
        this.connectionTimeout = connectionTimeout;

        this.zkClient = new ZkClient(this.zkUrl, this.sessionTimeout, this.connectionTimeout, ZKStringSerializer$.MODULE$);
//        return ZkUtils.apply(this.zkUrl, this.sessionTimeout, this.connectionTimeout, JaasUtils.isZkSecurityEnabled());
        this.zkUtils = ZkUtils.apply(this.zkClient, JaasUtils.isZkSecurityEnabled());
    }

    public OperationUtils(String host, Integer port, Integer sessionTimeout, Integer connectionTimeout,
                          Boolean isZkSecurityEnabled) {
        this.zkUrl = getUrl(host, port);
        this.sessionTimeout = sessionTimeout;
        this.connectionTimeout = connectionTimeout;

        this.zkClient = new ZkClient(this.zkUrl, this.sessionTimeout, this.connectionTimeout, ZKStringSerializer$.MODULE$);
//        return ZkUtils.apply(this.zkUrl, this.sessionTimeout, this.connectionTimeout, JaasUtils.isZkSecurityEnabled());
        this.zkUtils = ZkUtils.apply(this.zkClient, isZkSecurityEnabled);
    }

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

    public void createTopic(String topicName, Integer partitions, Integer replication, Properties properties, RackAwareMode rackAwareMode) {
        Assert.assertTrue(null != partitions && partitions.intValue() > 0);
        Assert.assertTrue(null != replication && replication.intValue() > 0);
        Assert.assertNotNull(properties);
        try {
            AdminUtils.createTopic(zkUtils, topicName, partitions, replication, properties, rackAwareMode);
        } catch (Exception e) {
            logger.error("createTopic error.", e);
        } finally {
            this.closeZkClient();
        }
    }

    public void createTopic(String topicName, Integer partitions, Integer replication, Properties properties) {
        this.createTopic(topicName, partitions, replication, properties, RackAwareMode.Enforced$.MODULE$);
    }

    public void createTopic(String topicName, Integer partitions, Integer replication) {
        this.createTopic(topicName, partitions, replication, AdminUtils.createTopic$default$5(), AdminUtils.createTopic$default$6());
    }

    public void createTopic_2(String topicName, Integer partitions, Integer replication) {
        String[] options = new String[]{
                "--create",
                "--zookeeper",
                this.zkUrl,
                "--partitions",
                partitions.toString(),
                "--topic",
                topicName,
                "--replication-factor",
                replication.toString()
        };
        TopicCommand.main(options);
    }

    private void closeZkClient() {
        if(null != this.zkClient) {
            this.zkClient.close();
        }
    }

    private String getUrl(String host, Integer port) {
        if(Strings.isNullOrEmpty(host)) { host = "localhost"; }
        if(null == port) { port = new Integer(2181); }
        return Joiner.on(":").useForNull("").join(host, port);
    }

    public static void main(String[] args) {
    }

 }
