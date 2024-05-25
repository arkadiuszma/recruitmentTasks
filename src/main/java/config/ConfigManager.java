package config;

public class ConfigManager {
    private static ConfigManager instance;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }
}
