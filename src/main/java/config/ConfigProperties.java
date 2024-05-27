package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigProperties {
    private static final Config config = ConfigFactory.load("config.conf");

    //UI
    public static String baseUIUrl = config.getString("baseUIUrl");
    public static String browser = config.getString("browser");
    public static Long timeout = config.getLong("timeout");
    public static Long loadPageTimeout = config.getLong("loadPageTimeout");

    //API
    public static String baseAPIUrl = config.getString("baseAPIUrl");
}