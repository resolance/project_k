package ru.ezhov.monitor.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class PathConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private String basicPath = "basic";

    @Test
    public void constructExceptionPathFolder() throws Exception {
        File file = temporaryFolder.newFolder(basicPath);

        PathConstructor pathConstructor = new PathConstructor(file.getAbsolutePath());
        String pathHolder = pathConstructor.constructExceptionPathFolder();

        String pathToFolderException =
                file
                        .getAbsolutePath()
                        + File.separator
                        + AppConfigInstance.getConfig().folderExceptionFile();

        assertEquals("Folders for exception", pathToFolderException, pathHolder);
    }

    @Test
    public void constructErrorPathFolder() throws Exception {
        File file = temporaryFolder.newFolder(basicPath);

        PathConstructor pathConstructor = new PathConstructor(file.getAbsolutePath());
        String pathHolder = pathConstructor.constructErrorPathFolder();

        String pathToFolderError =
                file
                        .getAbsolutePath()
                        + File.separator
                        + AppConfigInstance.getConfig().folderErrorFile();

        assertEquals("Folders for errors", pathToFolderError, pathHolder);

    }

}