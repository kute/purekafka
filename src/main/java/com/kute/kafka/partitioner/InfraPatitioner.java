package com.kute.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.List;
import java.util.Map;

/**
 * default:  org.apache.kafka.clients.producer.internals.DefaultPartitioner�������㷨���£�
 * 1. ����ָ��partition����keyʱ����key��hashֵ���㣬���key�ǹ̶��ģ���ô������ķ���Ҳ�ǹ̶���
 * 2. ��partition��key��û��ʱ������ȷ���
 * ��
 *
 * Created by kute on 2017/4/9.
 */
public class InfraPatitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte keyBytes[], Object value, byte valueBytes[], Cluster cluster) {
        List partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        List avaliablePartitions = cluster.availablePartitionsForTopic(topic);
        int avaliableNumPartitions = avaliablePartitions.size();
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
