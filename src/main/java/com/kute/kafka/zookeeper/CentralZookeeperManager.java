package com.kute.kafka.zookeeper;

import com.kute.kafka.util.KafkaConstants;
import com.kute.kafka.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by longbai on 2017/4/14.
 */
public class CentralZookeeperManager {

    private static final Logger logger = LoggerFactory.getLogger(CentralZookeeperManager.class);

    private static CentralZookeeperManager instance = new CentralZookeeperManager();

    public CentralZookeeperManager() {

        Properties prop = PropertiesLoader.loadPropertyFile(KafkaConstants.ZOOKEEPER_CONFIG_FILE);

    }

    public static void main(String[] args) {


    }

}
