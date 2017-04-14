package com.kute.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.List;
import java.util.Map;

/**
 * default:  org.apache.kafka.clients.producer.internals.DefaultPartitioner（分配算法如下：
 * 1. 当不指定partition但有key时，按key的hash值计算，如果key是固定的，那么最后分配的分区也是固定的
 * 2. 当partition，key都没有时，则均匀分配
 * ）
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
