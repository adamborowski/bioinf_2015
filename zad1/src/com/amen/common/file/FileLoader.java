/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common.file;

import static com.amen.common.log.Log.Error;
import static com.amen.common.log.Log.Info;
import static com.amen.common.log.Log.Warn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author AmeN
 */
public class FileLoader {

    private static File getFile(String p_path) {
        File t_file = null;
        try {
            t_file = new File(p_path);
            if (t_file.exists()) {
                return t_file;
            } else {
                Info(FileLoader.class, "File does not exist : " + p_path);
            }
        } catch (Exception e) {
            Error(FileLoader.class, "Loading file : " + e.getMessage());
        }
        return null;
    }

    public static String readFile(String p_path) {
        File t_givenFile = getFile(p_path);
        if (t_givenFile == null) {
            Warn(FileLoader.class, "Without file there is no data. Please deliver file " + p_path);
            System.exit(1);
        }

        StringBuilder t_stringBuilder = new StringBuilder();
        BufferedReader t_reader = null;
        try {
            t_reader = new BufferedReader(new FileReader(t_givenFile));
            String t_line = null;
            while (t_reader.ready()) {
                t_line = t_reader.readLine();

                if (t_line == null) {
                    break;
                }
                t_stringBuilder.append(t_line);
            }
            t_reader.close();

            return t_stringBuilder.toString();
        } catch (Exception e) {
            Error(FileLoader.class, "Error while reading file : " + p_path);
            System.exit(2);
        }

        return null;
    }

    public static String readLine(String p_path, int p_lineNumber) {
        File t_givenFile = getFile(p_path);
        if (t_givenFile == null) {
            Warn(FileLoader.class, "Without file there is no data. Please deliver file " + p_path);
            System.exit(1);
        }

        BufferedReader t_reader = null;
        try {
            t_reader = new BufferedReader(new FileReader(t_givenFile));
            String t_line = null;
            Integer t_lineNumber = 0;
            while (t_reader.ready()) {
                t_line = t_reader.readLine();

                if (t_line == null) {
                    break;
                } else if (p_lineNumber == p_lineNumber) {
                    break;
                }
            }
            t_reader.close();

            return t_line;
        } catch (Exception e) {
            Error(FileLoader.class, "Error while reading file : " + p_path);
            System.exit(2);
        }

        return null;
    }
}
