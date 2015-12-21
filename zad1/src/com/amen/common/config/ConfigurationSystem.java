package com.amen.common.config;

import static com.amen.common.log.Log.Error;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AmeN
 */
public class ConfigurationSystem {

    public static enum OS_TYPE {

        Windows, Linux, iOS
    }
    // os settings
    private static String systemName;
    private static String systemVersion;
    private static String systemArchitecture;
    private static OS_TYPE operatingSystemType;
    // other system settings 
    private static final String userName;
    private static final String userDirectory;

    //
    static {
        systemName = System.getProperty("os.name").toLowerCase();
        systemVersion = System.getProperty("os.version");
        systemArchitecture = System.getProperty("os.arch");

        userDirectory = System.getProperty("user.dir");
        userName = System.getProperty("user.name");

        System.out.println("Config is : " + systemName + " / " + systemVersion + " / " + systemArchitecture);
        setOsType();
    }

    public String getOsName() {
        return systemName;
    }

    private static void setOsType() {
        if (systemName.toLowerCase().contains("windows")) {
            System.out.println("OS type is Windows...");
            operatingSystemType = OS_TYPE.Windows;
        } else {
            System.out.println("OS type is Unix [POSIX]");
            operatingSystemType = OS_TYPE.Linux;
        }
    }

    public OS_TYPE getOsType() {
        return operatingSystemType;
    }

    public String getUserDirectory() {
        return userDirectory;
    }

    public String getWorkingDirectory() {
        return DirectoryWalker.getWorkingDirectory();
    }
}
