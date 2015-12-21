/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common.config;

import static com.amen.common.log.Log.Info;
import com.typesafe.config.ConfigFactory;
import java.io.File;

/**
 *
 * @author AmeN
 */
public class ConfigurationFile {

    private static final com.typesafe.config.Config configuration;
    private static final String configurationFilename = "config.properties";
    private static final String configurationFilePath;
    private static final String logSettingCatalog = "config";
    private static final String dirConfig;

    public static String getConfigPath() {
        return dirConfig;
    }

    static {
        dirConfig = DirectoryWalker.appendDirectory(System.getProperty("user.dir"), logSettingCatalog);
        com.typesafe.config.Config configurationAlternative = null;
        configurationFilePath = DirectoryWalker.appendFilename(System.getProperty("user.dir"), configurationFilename);
        Info(ConfigurationFile.class, "Primary path: " + configurationFilePath);

        configurationAlternative = ConfigFactory.parseFile(new File(configurationFilePath));
        com.typesafe.config.Config fallback = ConfigFactory.parseResources(configurationFilename);
        configuration = configurationAlternative.withFallback(fallback);
        Info(ConfigurationFile.class, "Backup path: " + configurationFilename);
    }

    public static boolean hasPath(String path) {
        return configuration.hasPath(path);
    }

    public static boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    public static int getInt(String path) {
        return configuration.getInt(path);
    }

    public static long getLong(String path) {
        return configuration.getLong(path);
    }

    public static double getDouble(String path) {
        return configuration.getDouble(path);
    }

    public static String getString(String path) {
        return configuration.getString(path);
    }

}
