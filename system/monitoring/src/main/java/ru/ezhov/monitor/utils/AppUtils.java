package ru.ezhov.monitor.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Класс с константами приложения, если что вынести в файл
 */

public class AppUtils {
    public static final String FILE_EXTENSION = ".json";
    public static final String FILE_FINAL_EXCEPTION= "error_";
    public static final String FOLDER_EXCEPTION_FILE = "repeat";
    public static final int ATTEMPTS_COUNT = 3;
    public static final int TIME_MILLISECONDS_CHECK_ERROR_FILES = 10000;

    public static final String PATTERN_DT_FILE = "yyyyMMddHHmmss";
    public static final String PATTERN_IP_FILE = "%s.%s.%s.%s";
    public static final String DELIMETER_PATTERNS = "_";
    public static final String REGEXP_CHECK_IP = "(\\d{1,3}\\.){3}(\\d{1,3})";

    public synchronized static String errorFolderPath(String pathBasic) {
        return pathBasic + File.separator + FOLDER_EXCEPTION_FILE;
    }
}
