package br.com.essenciaviva.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPS = new Properties();
    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) PROPS.load(in);
        } catch (IOException ignored) { }
    }
    private Config() { }
    public static String get(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) value = PROPS.getProperty(key, fallback);
        return value;
    }
    public static int sessionMinutes() { return Integer.parseInt(get("SESSION_MINUTES", "15")); }
}
