package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.utils.AppUtils;

import java.io.File;

import static org.junit.Assert.*;

public class ErrorFileMoverTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    public void move() throws Exception {
        String folderExceptionFile = AppUtils.FOLDER_EXCEPTION_FILE;

        File fileSrc = temporaryFolder.newFile("1.json");
        File folderDist = temporaryFolder.newFolder(folderExceptionFile);
        File fileDist = temporaryFolder.newFile(folderExceptionFile + "/1.json");
        ErrorFileMover errorFileMover = new ErrorFileMover(fileSrc, fileDist, 5);
        errorFileMover.move();
    }

}