package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

public class FileMoverExceptionTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    public void move() throws Exception {
        String folderExceptionFile = AppConfigInstance.getConfig().folderExceptionFile();

        File fileSrc = temporaryFolder.newFile("1.json");
        File folderDist = temporaryFolder.newFolder(folderExceptionFile);
        File fileDist = temporaryFolder.newFile(folderExceptionFile + "/1.json");
        FileMoverException fileMoverException = new FileMoverException();
        fileMoverException.move(fileSrc, fileDist, 5);
    }

}