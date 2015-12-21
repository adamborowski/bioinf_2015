/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import com.amen.common.config.ConfigurationSystem;
import com.amen.common.config.DirectoryWalker;
import com.amen.common.log.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AmeN
 */
public class AppRuntime {

    public static final String PARAM_CONFIG_FILE = "--configFile";
    public static final String PARAM_SEQ1 = "--seq1";
    public static final String PARAM_SEQ2 = "--seq2";
    public static final String PARAM_GEN_SIZE = "--genSize";

    private static final ConfigurationSystem sSysConfig;
    private static final Map<String, AppParameter> sParameters;
    private static final String applicationDirectory;

    static {
        sSysConfig = new ConfigurationSystem();
        sParameters = new HashMap<>();
        sParameters.put(PARAM_CONFIG_FILE, new AppParameter(PARAM_CONFIG_FILE));
        sParameters.put(PARAM_SEQ1, new AppParameter(PARAM_SEQ1, "ACACACTA"));
        sParameters.put(PARAM_SEQ2, new AppParameter(PARAM_SEQ2, "AGCACACA"));
        sParameters.put(PARAM_GEN_SIZE, new AppParameter(PARAM_GEN_SIZE, "6"));

        applicationDirectory = DirectoryWalker.appendDirectory(sSysConfig.getUserDirectory(), "Program");
        try {
            if (Files.notExists(Paths.get(applicationDirectory), LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectory(Paths.get(applicationDirectory));
            }
        } catch (IOException ex) {
            Log.Fatal(AppRuntime.class, "Application directory can not be created!", ex);
        }
    }

    public static ConfigurationSystem getSystemConfiguration() {
        return sSysConfig;
    }

    public static void parseParameters(String[] args) {
        List<String> argsProvided = Arrays.asList(args);

        argsProvided.stream().forEach((parameter) -> {
            try {
                if (!parameter.contains("=")) {
                    throw new Exception(String.format("Argument \"%s\"unrecognized.", parameter));
                }
                if (sParameters.containsKey(parameter.split("=")[0])) {
                    sParameters.get(parameter.split("=")[0]).setValue(parameter.split("=")[1]);
                }
            } catch (Exception e) {
                Log.Error(AppRuntime.class, e);
            }
        });
    }

    public static String getAlternativeConfig() {
        return sParameters.get(PARAM_CONFIG_FILE).getValue();
    }

    public static String getAppDirectory() {
        return applicationDirectory;
    }

    public static String getParameterValue(String p_PARAM_TYPE) {
        return sParameters.get(p_PARAM_TYPE) == null ? null : sParameters.get(p_PARAM_TYPE).getValue();
    }
}
