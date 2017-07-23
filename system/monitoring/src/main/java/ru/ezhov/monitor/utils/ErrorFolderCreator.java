package ru.ezhov.monitor.utils;

import java.io.File;

public class ErrorFolderCreator {
    private final String pathBasic;

    public ErrorFolderCreator(String pathBasic) {

        this.pathBasic = pathBasic;

    }

    public void checkAndCreateFolderExceptionFiles() {
        checkAndCreate(new PathConstructor(pathBasic).constructExceptionPathFolder());
    }

    public void checkAndCreateFolderErrorFiles() {
        checkAndCreate(new PathConstructor(pathBasic).constructErrorPathFolder());
    }

    private void checkAndCreate(String checkAndCreatePathFolder) {
        File fileParent = new File(checkAndCreatePathFolder);
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
    }
}
