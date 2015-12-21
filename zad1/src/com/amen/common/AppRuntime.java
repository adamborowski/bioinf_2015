/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common;

import com.amen.common.config.ConfigurationSystem;
import com.amen.common.config.DirectoryWalker;
import com.amen.common.file.FileLoader;
import com.amen.common.log.Log;
import static com.amen.common.log.Log.Error;
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
    public static final String PARAM_SEQ1F = "--seq1File";
    public static final String PARAM_SEQ2F = "--seq2File";
    public static final String PARAM_SIMILARITYF = "--similarityFile";
    public static final String PARAM_GAP_PENALTY = "--gapPenalty";

    private static final ConfigurationSystem sSysConfig;
    private static final Map<String, AppParameter> sParameters;
    private static final String applicationDirectory;

    static {
        sSysConfig = new ConfigurationSystem();
        sParameters = new HashMap<>();
        sParameters.put(PARAM_CONFIG_FILE, new AppParameter(PARAM_CONFIG_FILE));
        sParameters.put(PARAM_SEQ1, new AppParameter(PARAM_SEQ1, "TTTACACACGGG"));
        sParameters.put(PARAM_SEQ2, new AppParameter(PARAM_SEQ2, "ACACAC"));
        sParameters.put(PARAM_SEQ1F, new AppParameter(PARAM_SEQ1F, null));
        sParameters.put(PARAM_SEQ2F, new AppParameter(PARAM_SEQ2F, null));
        sParameters.put(PARAM_SIMILARITYF, new AppParameter(PARAM_SIMILARITYF, null));
        sParameters.put(PARAM_GAP_PENALTY, new AppParameter(PARAM_GAP_PENALTY, "-2"));

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

    public static String getInputA() {
        String file1 = getParameterValue(PARAM_SEQ1F);
        String inputA = null;
        if (file1 != null) {
            inputA = FileLoader.readFile(file1);
        } else {
            inputA = getParameterValue(PARAM_SEQ1);
        }
        return inputA;
    }

    public static String getInputB() {
        String file2 = getParameterValue(PARAM_SEQ2F);
        String inputB = null;
        if (file2 != null) {
            inputB = FileLoader.readFile(file2);
        } else {
            inputB = getParameterValue(PARAM_SEQ2);
        }
        return inputB;
    }

    static Double[][] getInputSimilarityMatrix() {
        Double[][] matrix = null;

        try {
            String fileS = getParameterValue(PARAM_SIMILARITYF);
            String similarityMatrix = null;
            if (fileS != null) {
                similarityMatrix = FileLoader.readFile(fileS);
            } else {
                similarityMatrix = null;
            }
            if (similarityMatrix != null) {
                int iterator = 0;
                String[] splits = similarityMatrix.split(";");
                Integer sizeX = Integer.parseInt(splits[iterator++]);
                Integer sizeY = Integer.parseInt(splits[iterator++]);

                matrix = new Double[sizeX][sizeY];

                for (int x = 0; x < sizeX; x++) {
                    for (int y = 0; y < sizeY; y++) {
                        Double parsedValue = Double.parseDouble(splits[iterator++]);
                        matrix[x][y] = parsedValue;
                    }
                }
            }
        } catch (Exception e) {
            Error(AppRuntime.class, "Error while reading your input file with similarity matrix.");
            e.printStackTrace();
            return null;
        }
        return matrix;
    }
}
