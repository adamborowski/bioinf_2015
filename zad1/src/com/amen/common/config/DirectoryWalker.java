/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common.config;

import static com.amen.common.log.Log.Error;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author AmeN
 */
public class DirectoryWalker {

    private static final FileSystem FILE_SYSTEM;

    static {
        FILE_SYSTEM = FileSystems.getDefault();
    }

    public static String appendDirectory(String path, String dirName) {

        if (!path.endsWith(FILE_SYSTEM.getSeparator())) {
            path += FILE_SYSTEM.getSeparator();
        }
        path += dirName + FILE_SYSTEM.getSeparator();
        return path;
    }

    public static String appendFilename(String path, String fileName) {
        if (!path.endsWith(FILE_SYSTEM.getSeparator())) {
            path += FILE_SYSTEM.getSeparator();
        }
        path += fileName;
        return path;
    }

    public static String getWorkingDirectory() {
        String tmpVar;
        Path tmpPath;

        tmpVar = System.getProperty("user.dir") + FILE_SYSTEM.getSeparator() + "Store" + FILE_SYSTEM.getSeparator();
        try {
            tmpPath = Paths.get(tmpVar);
            if (!Files.exists(tmpPath, NOFOLLOW_LINKS)) {
                Files.createDirectory(tmpPath);
            }
        } catch (IOException ex) {
            Error(DirectoryWalker.class, "Working directory is somehow unavailable. Can't create directory. -> path is : " + tmpVar);
            System.exit(2);
        }

        return tmpVar;
    }
}
