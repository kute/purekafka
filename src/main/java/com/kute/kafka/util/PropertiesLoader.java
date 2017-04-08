package com.kute.kafka.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kute on 2017/4/8.
 */
public class PropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);


    public static Properties loadPropertyFile(String fileName) {
        if(Strings.isNullOrEmpty(fileName)) {
            throw new IllegalArgumentException("Properties fileName is empty.");
        }
        Properties pros = new Properties();
        InputStream fileInputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName);
        try {
            pros.load(fileInputStream);
        } catch (FileNotFoundException e) {
            logger.error("File [{}] not found.{}", new Object[]{fileName, e});
        } catch (IOException e) {
            logger.error("IO exception occur.", e);
        } catch (Exception e) {
            logger.error("Load properties error.", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                // nothing to do
            }
        }
        return pros;
    }

}
