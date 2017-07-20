package ru.ezhov.monitor.utils;

import java.io.File;

public class ErrorFolderCreator {
    private final String pathBasic;

    public ErrorFolderCreator(String pathBasic) {
        this.pathBasic = pathBasic;
    }

    public void checkAndCreateFolderErrorFiles() {
        File fileParent = new File(AppUtils.errorFolderPath(pathBasic));
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }
}
