package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

    private static final Logger LOGGER;
    private static final Properties properties = new Properties();

    static {
        LOGGER = LoggerFactory.getLogger(ConfigReader.class);
        try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error("Error loading test data file", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
