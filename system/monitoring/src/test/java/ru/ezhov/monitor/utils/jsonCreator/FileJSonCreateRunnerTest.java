package ru.ezhov.monitor.utils.jsonCreator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileJSonCreateRunnerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    public void run() throws IOException {

        File file = temporaryFolder.newFolder();

        File fileFolder = new File(file.getAbsolutePath());

        int howManyFilesCreate = 20;
        FileJSonCreateRunner fileJSonCreateRunner =
                new FileJSonCreateRunner(howManyFilesCreate, fileFolder.getAbsolutePath());
        fileJSonCreateRunner.run();

        assertTrue(fileFolder.listFiles().length > 0);

    }

}