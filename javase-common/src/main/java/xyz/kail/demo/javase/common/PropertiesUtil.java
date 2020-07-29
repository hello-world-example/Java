package xyz.kail.demo.javase.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROP = new Properties();

    static {
        try {
            PROP.load(PropertiesUtil.class.getResourceAsStream("/key.properties"));
        } catch (IOException e) {
            // noting
        }
    }

    public static void register(InputStream in) throws IOException {
        PROP.load(in);
    }

    public static String getStringDefaultNull(String key) {
        return getString(key, null);
    }


    public static String getStringDefaultBlank(String key) {
        return getString(key, "");
    }


    public static String getString(String key, String defaultValue) {
        return PROP.getProperty(key, defaultValue);
    }

}
