package com.kute.kafka.serializer;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Created by kute on 2017/5/8.
 */
public class KryoDeserializer implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        return KryoUtil.deserialize(bytes);
    }

    @Override
    public void close() {

    }
}
