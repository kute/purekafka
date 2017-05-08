package com.kute.kafka.serializer;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by kute on 2017/5/8.
 */
public class JsonSerializer<T> implements Serializer<T> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, T o) {
        return gson.toJson(o).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void close() {

    }
}
