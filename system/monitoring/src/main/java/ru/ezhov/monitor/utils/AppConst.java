package ru.ezhov.monitor.utils;

import java.io.File;

/**
 * Класс с константами приложения, если что вынести в файл
 */

public class AppConst {
    public static final String FILE_EXTENSION = ".json";
    public static final String PRE_FILE = "repeat_";
    public static final String FOLDER_EXCEPTION_FILE = "repeat";
    public static final int ATTEMPTS_COUNT = 3;

    public synchronized static String errorFolderPath(String pathBasic){
        return pathBasic + File.separator + FOLDER_EXCEPTION_FILE;
    }
}
