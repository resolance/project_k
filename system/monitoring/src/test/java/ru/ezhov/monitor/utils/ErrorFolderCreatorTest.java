package ru.ezhov.monitor.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class ErrorFolderCreatorTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String basicPath = "test";

    @Test
    public void checkAndCreateFolderExceptionFiles() throws Exception {

        File file = temporaryFolder.newFolder(basicPath);

        ErrorFolderCreator errorFolderCreator = new ErrorFolderCreator(file.getAbsolutePath());
        errorFolderCreator.checkAndCreateFolderExceptionFiles();


    }

    @Test
    public void checkAndCreateFolderErrorFiles() throws Exception {
        File file = temporaryFolder.newFolder(basicPath);

        ErrorFolderCreator errorFolderCreator = new ErrorFolderCreator(file.getAbsolutePath());
        errorFolderCreator.checkAndCreateFolderExceptionFiles();

    }

}