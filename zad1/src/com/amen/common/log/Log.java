/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amen.common.log;

import com.typesafe.config.Config;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Paweł Recław <pawel.reclaw@gmail.com> #AmeN
 */
@SuppressWarnings("unchecked")
public class Log {

    private static enum LOG_LEVEL_ENUM {

        lvl_error,
        lvl_debug,
        lvl_info,
        lvl_fatal,
        lvl_warn;

        private static LOG_LEVEL_ENUM parse(String split) {
            if (split.equalsIgnoreCase("lvl_error")) {
                return lvl_error;
            } else if (split.equalsIgnoreCase("lvl_debug")) {
                return lvl_debug;
            } else if (split.equalsIgnoreCase("lvl_info")) {
                return lvl_info;
            } else if (split.equalsIgnoreCase("lvl_fatal")) {
                return lvl_fatal;
            } else if (split.equalsIgnoreCase("lvl_warn")) {
                return lvl_warn;
            }
            return null;
        }
    }
    private static String sConfigPath;
    private static Boolean LOG_VALID = true;
    private final static int LOG_LEVEL = 0;
    private final static LogGenerator sLogGenerator;
    private static final Boolean LOG_LEVEL_INFO = true;          //0
    private static final Boolean LOG_LEVEL_FATAL = true;         //0
    private static final Boolean LOG_LEVEL_ERROR = true;         //>=2
    private static final Boolean LOG_LEVEL_WARN = true;          //>=4
    private static final Boolean LOG_STACK = true;               //2/6/10
    private static final Boolean LOG_LEVEL_DEBUG = true;         //>=8

    static {
        sLogGenerator = new LogGenerator(System.getProperty("user.dir"));
        try {
            sLogGenerator.validateLoggerConfig();
            sConfigPath = sLogGenerator.getLogConfigPath();
        } catch (IOException ioException) {
            LOG_VALID = false;
            System.err.println("Exiting for there has been an exception - : > ");
            System.err.println("CLASS: " + ioException.getClass());
            System.err.println("MSG: " + ioException.getMessage());
            
            ioException.printStackTrace();

            System.exit(1);
        }

        init();
    }
    private static boolean s_initialized = false;

    public static void init() {
        if (!s_initialized) {
            PropertyConfigurator.configure(sConfigPath);

//            m_colors_supported.put(Color.red, "31");
            s_initialized = true;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Logger ERROR">
    /**
     * LOG ERRORS. Will log information only if log level is on level higher or
     * equal to 5.
     *
     * @param clas
     * @param message
     */
    public static void Error(Class<?> clas, String message) {
        if (doIIgnore(message, clas, LOG_LEVEL_ENUM.lvl_error)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if ((LOG_LEVEL_ERROR == null || LOG_LEVEL_ERROR)) {
            Logger.getLogger(clas).error(message);
            System.err.println(message);
        }
    }

    public static void Error(Class<?> clas, Throwable message) {
        if (doIIgnore(message.getMessage(), clas, LOG_LEVEL_ENUM.lvl_error)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if ((LOG_LEVEL_ERROR == null || LOG_LEVEL_ERROR)) {
            Logger.getLogger(clas).error(message.getMessage());
            System.err.println(message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }

    public static void Error(Class<?> clas, String txtMessage, Throwable message) {
        if (doIIgnore(txtMessage, clas, LOG_LEVEL_ENUM.lvl_error)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
        } else if (LOG_LEVEL_ERROR == null || LOG_LEVEL_ERROR) {
            Logger.getLogger(clas).error(txtMessage + " " + message.getMessage());
            System.err.println(txtMessage + " " + message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logger WARN">
    public static void Warn(Class<?> clas, String message) {
        if (doIIgnore(message, clas, LOG_LEVEL_ENUM.lvl_warn)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_WARN == null || LOG_LEVEL_WARN) {
            Logger.getLogger(clas).warn(message);
            System.err.println(clas + " " + message);
        }
    }

    public static void Warn(Class<?> clas, Throwable message) {
        if (doIIgnore(message.getMessage(), clas, LOG_LEVEL_ENUM.lvl_warn)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_WARN == null || LOG_LEVEL_WARN) {
            Logger.getLogger(clas).warn(message.getMessage());
            System.err.println(clas + " " + message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }

    public static void Warn(Class<?> clas, String txtMessage, Throwable message) {
        if (doIIgnore(txtMessage, clas, LOG_LEVEL_ENUM.lvl_warn)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
        } else if (LOG_LEVEL_WARN == null || LOG_LEVEL_WARN) {
            Logger.getLogger(clas).warn(txtMessage + " " + message.getMessage());
            System.err.println(txtMessage + " " + message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logger INFO">
    public static void Info(Class<?> clas, String message) {
        if (doIIgnore(message, clas, LOG_LEVEL_ENUM.lvl_info)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_INFO == null || LOG_LEVEL_INFO) {
            Logger.getLogger(clas).info(message);
        }
    }

    public static void Info(Class<?> clas, Throwable message) {
        if (doIIgnore(message.getMessage(), clas, LOG_LEVEL_ENUM.lvl_info)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_INFO == null || LOG_LEVEL_INFO) {
            Logger.getLogger(clas).info(message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }

    public static void Info(Class<?> clas, String txtMessage, Throwable message) {
        if (doIIgnore(txtMessage, clas, LOG_LEVEL_ENUM.lvl_info)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
        } else if (LOG_LEVEL_INFO == null || LOG_LEVEL_INFO) {
            Logger.getLogger(clas).info(txtMessage + " " + message.getMessage());
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logger DEBUG">
    public static void Debug(Class<?> clas, String message) {
        if (doIIgnore(message, clas, LOG_LEVEL_ENUM.lvl_debug)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_DEBUG == null || LOG_LEVEL_DEBUG) {
            Logger.getLogger(clas).debug(message);
            //System.out.println(message);
        }
    }

    public static void Debug(Class<?> clas, Throwable message) {
        if (doIIgnore(message.getMessage(), clas, LOG_LEVEL_ENUM.lvl_debug)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + message);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + message);
        } else if (LOG_LEVEL_DEBUG == null || LOG_LEVEL_DEBUG) {
            Logger.getLogger(clas).debug(message.getMessage());
            //System.out.println(message);
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }

    public static void Debug(Class<?> clas, String txtMessage, Throwable message) {
        if (doIIgnore(txtMessage, clas, LOG_LEVEL_ENUM.lvl_debug)) {
            return;
        }
        if (!LOG_VALID) {
            System.err.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
            System.out.println((clas == null ? clas : clas.getName()) + "  -" + txtMessage);
        } else if (LOG_LEVEL_DEBUG == null || LOG_LEVEL_DEBUG) {
            Logger.getLogger(clas).debug(txtMessage + " " + message.getMessage());
            //System.out.println(message);
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logger FATAL">
    /**
     * Writes down fatal information (log level must be set to at least $?)
     *
     * @param clas
     * @param message
     */
    public static void Fatal(Class<?> clas, String message) {
        if (doIIgnore(message, clas, LOG_LEVEL_ENUM.lvl_fatal)) {
            return;
        }
        if (LOG_LEVEL_FATAL == null || LOG_LEVEL_FATAL) {
            Logger.getLogger(clas).fatal(message);
            System.err.println("FATAL: " + clas + "/" + message);
        }
    }

    /**
     * Writes down fatal information (log level must be set to at least $?).
     * Given no message, printing it from exception.
     *
     * @param clas
     * @param message
     */
    public static void Fatal(Class<?> clas, Throwable message) {
        if (doIIgnore(message.getMessage(), clas, LOG_LEVEL_ENUM.lvl_fatal)) {
            return;
        }
        if (LOG_LEVEL_FATAL == null || LOG_LEVEL_FATAL) {
            Logger.getLogger(clas).fatal(message.getMessage());
            System.err.println("FATAL: " + clas + "/" + message);
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }

    /**
     * Writes down fatal information (log level must be set to at least $?).
     * Writing everything with control.
     *
     * @param clas
     * @param txtMessage
     * @param message
     */
    public static void Fatal(Class<?> clas, String txtMessage, Throwable message) {
        if (doIIgnore(message.getMessage() + " " + txtMessage, clas, LOG_LEVEL_ENUM.lvl_fatal)) {
            return;
        }
        if (LOG_LEVEL_FATAL == null || LOG_LEVEL_FATAL) {
            Logger.getLogger(clas).fatal(txtMessage + " " + message.getMessage());
            System.err.println("FATAL: " + clas + "/\\" + txtMessage + " /" + message);
            if (LOG_STACK == null || LOG_STACK) {
                message.printStackTrace();
            }
        }
    }
    // </editor-fold>

    /**
     * Writes out an information, that there is still a code that you want to
     * implement, and this will remind other logger-readers to remind it to you
     *
     * :)
     */
    public static void Reminder() {
        Logger.getLogger("NOT_A_BUG").warn(
                "PLEASE, REMIND YOUR PROGRAMMERS, THAT THIS SHOULD BE IMPLEMENTED!");
        System.err.println("PLEASE, REMIND YOUR PROGRAMMERS, THAT THIS SHOULD BE IMPLEMENTED!");
    }

    public static void Reminder(String p_additional) {
        Logger.getLogger("NOT_A_BUG").warn(
                "PLEASE, REMIND YOUR PROGRAMMERS, THAT THIS SHOULD BE IMPLEMENTED! Additional information: " + p_additional);
        System.err.println("PLEASE, REMIND YOUR PROGRAMMERS, THAT THIS SHOULD BE IMPLEMENTED! \\" + p_additional);
    }

//    /**
//     * Check if anything is going to be ignored. two loops around our
//     * containers.
//     * <p>
//     * @param someLog - log that needs to be posted.
//     * @param fromClass - check the class of log
//     * <p>
//     * @return
//     */
    private static boolean doIIgnore(String someLog, Class<?> fromClass, LOG_LEVEL_ENUM lvl_) {
//        try {
//            Logger.getLogger(fromClass).getParent().getLevel();
//            if (fromClass == null || fromClass.getCanonicalName() == null) {
//                fromClass = CLASS_UNAVAILABLE.class;
//            }
//
//            LOG_LEVEL_ENUM TYPE;
//            String TXT;
//            for (String x : s_ignored_packages) {
//                if (x.contains("&&&")) {
//                    TYPE = LOG_LEVEL_ENUM.parse(x.split("&&&")[0]);
//                    TXT = x.split("&&&")[1];
//                    if (fromClass.getCanonicalName().contains(x)
//                            || fromClass.toString().contains(x)
//                            || fromClass.toGenericString().contains(x)
//                            || someLog.contains(x)) {
//                        return true;
//                    }
//                } else {
//                    if (fromClass.getCanonicalName().contains(x)
//                            || fromClass.toString().contains(x)
//                            || fromClass.toGenericString().contains(x)
//                            || someLog.contains(x)) {
//                        return true;
//                    }
//                }
//            }
//            for (String w : s_ignored_phrases) {
//                if (fromClass.getCanonicalName().contains(w)
//                        || fromClass.toString().contains(w)
//                        || fromClass.toGenericString().contains(w)
//                        || someLog.contains(w)) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            Logger.getLogger(Log.class).error("\\\\\\\\\\ someLog:" + someLog + " \\\\\\\\\\\\ " + fromClass + " \\\\\\\\\\ " + lvl_);
//        }
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="Logger STACK_TRACE">
    /**
     * This is just to print some of stack trace. It depends on how verbose you
     * are.
     *
     * @param p_throwableObj - the object with stack trace.
     */
    public static void PrintTrace(Throwable p_throwableObj) {
        try {
            if (p_throwableObj != null) {
                StackTraceElement[] l_elements = p_throwableObj.getStackTrace();
                int lineCount = Math.min(LOG_LEVEL, l_elements.length);

                StringBuilder sb = new StringBuilder();
                sb.append("TRACE: \n");
                for (int i = 0; i < lineCount; i++) {
                    sb.append(l_elements[i]);
                }
                sb.append(logFinish);
                m_stackLogger.info(sb.toString());
            }
        } catch (Exception e) {
            Fatal(Log.class, "This is an irony. An error within an error.");
            System.exit(2);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Logger STACK_TRACE. OBJ">
    private static final String logBegin = "------- STACK TRACE BEGIN -------";
    private static final String logFinish = "------- STACK TRACE Finish -------";
    private static final Logger m_stackLogger = Logger.getLogger(logBegin);
    // </editor-fold>
    // </editor-fold>

    /**
     * Loading from configuration file level, on which we're going to log.
     * <p>
     * @param m_configuration - configuration file of type...
     * <p>
     * @return integer in range [ -10 - 10 ]
     */
    private static int loadLevel(Config m_configuration) {
        try {
            return m_configuration.getInt("Configuration.Log.Level");
        } catch (Exception e) {
            Error(Log.class,
                    "So i didn't find any logger, "
                    + "I'm assuming it's going to be 10.");
            return 10;
        }
    }

//    /**
//     * Checks if in config exists entry about ignoring any phrase, and if
//     * exists, it's parsing it into ignored parts...
//     * <p>
//     * @param m_configuration - configuration file
//     * <p>
//     * @return - arraylist of ignored classes ( it's ok to pass just name of
//     * class, no path to it.)
//     */
//    private static ArrayList<String> loadIgnored(Config m_configuration) {
//        ArrayList<String> l_list = new ArrayList<>();
//        try {
//            String l_classes = m_configuration.getString(
//                    "Configuration.Log.Ignored.Class");
//
//            l_list.addAll(Arrays.asList(l_classes.split(";")));
//            return l_list;
//        } catch (Exception e) {
//            return l_list;
//        }
//    }
//    /**
//     * Checks if in config exists entry about ignoring any phrase, and if
//     * exists, it's parsing it into ignored parts...
//     * <p>
//     * @param m_configuration - configuration file
//     * <p>
//     * @return - arraylist of ignored paths
//     */
//    private static ArrayList<String> loadIgnoredPackages(Config m_configuration) {
//        ArrayList<String> l_list = new ArrayList<>();
//        try {
//            String l_classes = m_configuration.getString(
//                    "Configuration.Log.Ignored.Package");
//
//            l_list.addAll(Arrays.asList(l_classes.split(";")));
//            return l_list;
//        } catch (Exception e) {
//            return l_list;
//        }
//    }
//    private static String getColorValue(Color c) throws Exception {
//        if (m_colors_supported.containsKey(c)) {
//            Debug(Log.class, "Coloring text...");
//            return m_colors_supported.get(c);
//        }
//
//        throw new Exception("This color is not supported.");
//    }
//    public static String setColored(String str, Color c) throws Exception {
//        StringBuilder sb = new StringBuilder("\u001b[");
//        sb.append("0");
//        sb.append(";");
//        sb.append(getColorValue(c));
//        sb.append("m");
//        sb.append(str);
//        sb.append("\u001b[m");
//
//        return sb.toString();
//    }
}
