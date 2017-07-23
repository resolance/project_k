package ru.ezhov.monitor;

import java.nio.file.Path;
import java.util.Timer;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.FileTreatment;
import ru.ezhov.monitor.fileTreatment.FileRepeatedTreatment;
import ru.ezhov.monitor.fileTreatment.FileMonitor;
import ru.ezhov.monitor.fileTreatment.FileOldTreatment;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;
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
        errorFolderCreator.checkAndCreateFolderExceptionFiles();
        errorFolderCreator.checkAndCreateFolderErrorFiles();

        //запуск проверки и обработки старых файлов
        FileOldTreatment fileOldTreatment = new FileOldTreatment(folder);
        fileOldTreatment.rename();

        Treatment<Runnable> pathTreatment = new FileTreatment();

        //запуск таймера на обработку ошибочных файлов
        Timer timer = new Timer();
        timer.schedule(
                new FileRepeatedTreatment(folder, pathTreatment),
                0,
                AppConfigInstance.getConfig().timeMillisecondsCheckErrorFiles());

        //запуск в отдельном потоке монитора файлов
        Thread thread = new Thread(new FileMonitor(folder, pathTreatment));
        thread.start();
    }
}
