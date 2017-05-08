package com.kute.kafka.serializer;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayOutputStream;

/**
 * Created by kute on 2017/5/8.
 */
public class KryoUtil {

    private static final KryoFactory factory = () -> KryoConfiger.getConfiger();

    private static final KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    public static byte[] serialize(final Object obj) {

        return pool.run(kryo -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            kryo.writeClassAndObject(output, obj);
            output.close();
            return stream.toByteArray();
        });
    }

    public static <V> V deserialize(final byte[] objectData) {

        return (V) pool.run(kryo -> {
            Input input = new Input(objectData);
            return (V) kryo.readClassAndObject(input);
        });
    }

    public static <V> V deepCopy(final V obj) {
        return (V) pool.run(kryo -> (V) kryo.copy(obj));
    }

}
