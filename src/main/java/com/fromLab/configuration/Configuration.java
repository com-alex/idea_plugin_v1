package com.fromLab.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author wsh
 * @date 2019-12-09
 */
public class Configuration {
    private static Properties p = null;
    static {
        try {
            String path = Configuration.class.getClassLoader().getResource("db.properties").getPath();
            p = new Properties();
            p.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return p.getProperty(key).toString();
    }
}
