package com.kute.kafka.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author bl
 *
 */
public class KafkaConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfigLoader.class);

    /**
     * 加载配置文件
     * 
     * @param inputStream
     * @return
     */
    public static Properties loadPropertyFile(InputStream inputStream) {
        Properties pros = new Properties();
        try {
            pros.load(inputStream);
            return pros;
        } catch (FileNotFoundException e) {
            logger.error("Cannot find kafka config file", e);
        } catch (IOException e) {
            logger.error("Cannot read kafka config file", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // nothing to do
            }
        }
        return null;
    }
}
