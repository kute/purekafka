package com.kute.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by kute on 2017/5/8.
 */
public class KryoSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        return KryoUtil.serialize(o);
    }

    @Override
    public void close() {

    }
}
