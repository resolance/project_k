package ru.ezhov.monitor;

import java.util.Timer;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.RepeatedFileTreatment;
import ru.ezhov.monitor.fileTreatment.FileMonitor;
import ru.ezhov.monitor.fileTreatment.OldFilesTreatment;
import ru.ezhov.monitor.utils.AppUtils;
import ru.ezhov.monitor.utils.ErrorFolderCreator;

/**
 * Запуск приложения
 * <p>
 *
 * @author ezhov_da
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());


    public static void main(String[] args) {
        //папка для обработки файлов
        String folder = args[0];

        //проверка существования папки с ошибками + ее создание
        ErrorFolderCreator errorFolderCreator = new ErrorFolderCreator(folder);
        errorFolderCreator.checkAndCreateFolderErrorFiles();

        //запуск проверки и обработки старых файлов
        OldFilesTreatment oldFilesTreatment = new OldFilesTreatment(folder);
        oldFilesTreatment.rename();

        //запуск таймера на обработку ошибочных файлов
        Timer timer = new Timer();
        timer.schedule(
                new RepeatedFileTreatment(folder),
                0,
                AppUtils.TIME_MILLISECONDS_CHECK_ERROR_FILES);

        //запуск в отдельном потоке монитора файлов
        Thread thread = new Thread(new FileMonitor(folder));
        thread.start();
    }
}
