package com.assessment.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class Configuration {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = load();

    private Configuration() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream stream = Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (stream == null) {
                throw new IllegalStateException(CONFIG_FILE + " was not found on the test classpath");
            }
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + CONFIG_FILE, e);
        }
    }

    private static String get(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration value for key: " + key);
        }
        return value.trim();
    }

    public static String baseUri() {
        return get("api.base.uri");
    }

    public static String basePath() {
        return get("api.base.path");
    }

    public static boolean logAll() {
        return Boolean.parseBoolean(get("api.log.all"));
    }
}
