package ru.clevertec.checkrunner.util;

import lombok.experimental.UtilityClass;
import org.hibernate.cfg.Environment;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@UtilityClass
public class PropertiesLoader {
    private static final Yaml YML = new Yaml();
    private static Map<String, Map<String, Object>> map;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.yml")) {
            map = YML.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        String[] split = key.split("\\.");
        Map<String, Object> stringMap = map.get(split[0]);
        return stringMap.get(split[1]).toString();
    }

    public Properties addProperties() {
        Properties properties = new Properties();

        properties.put(Environment.SHOW_SQL, get("hibernate.show_sql"));
        properties.put(Environment.FORMAT_SQL, get("hibernate.format_sql"));
        properties.put(Environment.DIALECT, get("hibernate.dialect"));

        return properties;
    }
}
