package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static org.junit.Assert.*;

public class FileMonitorTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void run() throws Exception {

        String nameTextFile = "test.json";

        File folderWait = temporaryFolder.newFolder("test");

        Treatment<Path> treatment = new Treatment<Path>() {
            @Override
            public void treatment(Path treatmentObject) {
                assertEquals(nameTextFile, treatmentObject.toFile().getName());
            }
        };

        FileMonitor fileMonitor = new FileMonitor(folderWait.getAbsolutePath(), treatment);
        Thread thread = new Thread(fileMonitor);
        thread.start();

        File fileNew = temporaryFolder.newFile(folderWait.getName() + File.separator + nameTextFile);

        Thread.sleep(2000);
        fileMonitor.stop();


    }

}