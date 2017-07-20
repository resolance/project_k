package ru.ezhov.monitor;

import java.util.Timer;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.FileLoaderTask;
import ru.ezhov.monitor.fileTreatment.FileMonitor;
import ru.ezhov.monitor.fileTreatment.OldFilesTreatment;

/**
 * Запуск приложения
 * <p>
 *
 * @author ezhov_da
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());


    public static void main(String[] args) {
        String folder = args[0];

        OldFilesTreatment oldFilesTreatment = new OldFilesTreatment(folder);
        oldFilesTreatment.rename();

        Timer timer = new Timer();
        timer.schedule(new FileLoaderTask(folder), 0, 5000);

        Thread thread = new Thread(new FileMonitor(folder));
        thread.start();
    }
}
