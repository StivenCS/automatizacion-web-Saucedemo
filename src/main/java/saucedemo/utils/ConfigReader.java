package saucedemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES = load();

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar config.properties", e);
        }
        return properties;
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
