package com.kyle.clientTracker.model;
//done
import com.kyle.clientTracker.Main;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * class to be able to retrieve config properites from anywhere
 * @author Kyle Gibson
 */

public abstract class Config {
    private static final String CONFIG_FILE = "config.properties";
    private static InputStream input = Main.class.getResourceAsStream(CONFIG_FILE);
    private static Properties props = new Properties();

    public static void setProps() throws IOException {
        props.load(input);
    }

    public static String getServerUrl() {
        return props.getProperty("server.url");
    }

    public static int getServerPort() {
        return Integer.parseInt(props.getProperty("server.port"));
    }

    public static String getClientTrustLocation() {
        return props.getProperty("client.trustLocation");
    }
    public static String getClientPassword() {
        return props.getProperty("client.password");
    }
}