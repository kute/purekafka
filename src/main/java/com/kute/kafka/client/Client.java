package com.kute.kafka.client;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.kute.kafka.dto.Book;
import com.kute.kafka.producer.InfraKafkaProducer;
import com.kute.kafka.util.KafkaConstants;

import java.util.List;

/**
 * Created by kute on 2017/3/24.
 */
public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.sendmsg();
    }

    public void sendmsg() {

        List<String > msgList = Lists.newArrayList();
        for (Integer i = 0; i < 10000; i++) {
            Book book = new Book(String.valueOf(i), "content " + i, "author_" + i, i + "234.54");
            String msg = JSONObject.toJSONString(book);
            msgList.add(msg);
        }
        Long start = System.currentTimeMillis();
        try {
            InfraKafkaProducer.getInstance().sendMessage(KafkaConstants.KAFKA_TOPIC, KafkaConstants.KAFKA_PARTITION_ID,
                    KafkaConstants.KAFKA_TOPIC_KEY, msgList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
