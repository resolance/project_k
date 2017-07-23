package ru.ezhov.monitor.utils.jsonCreator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class FileJsonCreatorTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void create() throws Exception {

        File fileFolder = temporaryFolder.newFolder();

        File file = new FileJsonCreator(fileFolder.getAbsolutePath()).create(10);

        assertTrue(fileFolder.listFiles().length > 0);


    }

}