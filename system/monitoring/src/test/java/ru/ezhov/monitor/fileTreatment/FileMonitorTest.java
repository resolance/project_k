package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

public class FileMonitorTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void run() throws Exception {

        String nameTextFile = "test.json";

        File folderWait = temporaryFolder.newFolder(AppConfigInstance.getConfig().folderExceptionFile());

        Treatment<Runnable> treatment = new Treatment<Runnable>() {
            @Override
            public void treatment(Runnable treatmentObject) {
                System.out.println("test");
            }
        };

        FileMonitor fileMonitor = new FileMonitor(folderWait.getAbsolutePath(), treatment);
        Thread thread = new Thread(fileMonitor);
        thread.start();

        File fileNew = temporaryFolder.newFile(folderWait.getName() + File.separator + nameTextFile);

        fileMonitor.stop();
    }

}