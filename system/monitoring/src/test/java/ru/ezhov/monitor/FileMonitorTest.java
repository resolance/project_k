package ru.ezhov.monitor;

import org.junit.Test;
import ru.ezhov.monitor.fileTreatment.FileLoaderTask;
import ru.ezhov.monitor.fileTreatment.FileMonitor;
import ru.ezhov.monitor.fileTreatment.OldFilesTreatment;

import java.util.Timer;

public class FileMonitorTest {
    @Test
    public void run() throws Exception {
        String folder = "C:\\Users\\rrnezh\\Desktop\\file_monitor";

        OldFilesTreatment oldFilesTreatment = new OldFilesTreatment(folder);
        oldFilesTreatment.rename();

        Timer timer = new Timer();
        timer.schedule(new FileLoaderTask(folder), 0, 5000);

        Thread thread = new Thread(new FileMonitor(folder));
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(3000);

        thread = new Thread(new FileJsonCreator(folder));
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(20000);
    }
}