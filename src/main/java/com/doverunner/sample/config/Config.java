package com.doverunner.sample.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration loader from properties file
 * Create 'config.properties' from 'config.properties.example' and fill in your values
 */
public class Config {

    // Configuration file name
    private static final String CONFIG_FILE_NAME = "config.properties";

    // Property keys - centralized management to avoid duplication and typos
    private static final String KEY_SITE_KEY = "doverunner.siteKey";
    private static final String KEY_ACCESS_KEY = "doverunner.accessKey";
    private static final String KEY_SITE_ID = "doverunner.siteId";
    private static final String KEY_USER_ID = "doverunner.userId";
    private static final String KEY_CONTENT_ID = "doverunner.contentId";

    private static final Properties properties = new Properties();

    // Private constructor to prevent instantiation
    private Config() {
        throw new IllegalStateException("Utility class");
    }

    // Load properties BEFORE initializing constants
    static {
        loadPropertiesFile();

        // Validate required configuration values
        validateRequired(KEY_SITE_KEY);
        validateRequired(KEY_ACCESS_KEY);
        validateRequired(KEY_SITE_ID);
        validateRequired(KEY_USER_ID);
        validateRequired(KEY_CONTENT_ID);
    }

    private static void loadPropertiesFile() {
        try {
            loadFromFileSystem();
        } catch (IOException e) {
            loadFromClasspath();
        }
    }

    private static void loadFromFileSystem() throws IOException {
        String configPath = determineConfigPath();
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        }
    }

    private static String determineConfigPath() {
        // Priority 1: Explicit config path via system property
        String configPath = System.getProperty("config.path");
        if (configPath != null && !configPath.trim().isEmpty()) {
            return configPath;
        }

        // Priority 2: Workspace root path + config.properties
        String workspaceRoot = System.getProperty("workspace.root.path");
        if (workspaceRoot != null && !workspaceRoot.trim().isEmpty()) {
            return workspaceRoot + "/" + CONFIG_FILE_NAME;
        }

        // Priority 3: Environment variable
        String envPath = System.getenv("DRM_CONFIG_PATH");
        if (envPath != null && !envPath.trim().isEmpty()) {
            return envPath;
        }

        // Priority 4: Default relative path (development environment)
        return CONFIG_FILE_NAME;
    }

    private static void loadFromClasspath() {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (input == null) {
                throw new RuntimeException(
                    CONFIG_FILE_NAME + " not found. Please copy " + CONFIG_FILE_NAME +
                    ".example to " + CONFIG_FILE_NAME + " and fill in your values."
                );
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + CONFIG_FILE_NAME, e);
        }
    }

    private static void validateRequired(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                "Config error: '" + key + "' is missing or empty. " +
                "Please edit config.properties and fill in your actual values."
            );
        }
        // Check if still using template placeholder values
        if (value.startsWith("<") && value.endsWith(">")) {
            throw new RuntimeException(
                "Config error: '" + key + "' still has placeholder value '" + value + "'. " +
                "Please edit config.properties and replace with your actual values."
            );
        }
    }

    // Initialize constants AFTER properties are loaded
    public static final String SITE_KEY = properties.getProperty(KEY_SITE_KEY);
    public static final String ACCESS_KEY = properties.getProperty(KEY_ACCESS_KEY);
    public static final String SITE_ID = properties.getProperty(KEY_SITE_ID);
    public static final String USER_ID = properties.getProperty(KEY_USER_ID);
    public static final String C_ID = properties.getProperty(KEY_CONTENT_ID);

}
