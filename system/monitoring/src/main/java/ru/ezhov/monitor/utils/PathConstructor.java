package ru.ezhov.monitor.utils;

import java.io.File;

/**
 * Конструктор путей к папкам исключений и ошибок
 */
public class PathConstructor {

    private AppConfig appConfig;
    private String basicPath;

    public PathConstructor(String basicPath) {
        this.basicPath = basicPath;
        appConfig = AppConfigInstance.getConfig();
    }

    public String constructExceptionPathFolder() {
        return construct(appConfig.folderExceptionFile());

    }

    public String constructErrorPathFolder() {
        return construct(appConfig.folderErrorFile());
    }

    private String construct(String folder) {
        return basicPath + File.separator + folder;
    }

}
