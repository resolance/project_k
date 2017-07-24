package ru.ezhov.monitor.fileTreatment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

public class FileMonitorImlTest {
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

        FileMonitorIml fileMonitorIml = new FileMonitorIml(folderWait.getAbsolutePath(), treatment);
        Thread thread = new Thread(fileMonitorIml);
        thread.start();

        File fileNew = temporaryFolder.newFile(folderWait.getName() + File.separator + nameTextFile);

        fileMonitorIml.stop();
    }

}