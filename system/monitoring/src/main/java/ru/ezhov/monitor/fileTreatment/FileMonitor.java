package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Stopper;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;

/**
 * Класс, который следит за изменением в файловой системе
 * и запускает обработку файлов
 * <p>
 *
 * @author ezhov_da
 */
public class FileMonitor implements Runnable, Stopper {
    private static final Logger LOG = Logger.getLogger(FileMonitor.class.getName());
    private String pathMonitor;
    private Treatment<Runnable> treatment;

    private AtomicBoolean isStopMonitor = new AtomicBoolean(false);

    private WatchKey watchKey;

    private AppConfig appConfig;

    /**
     * @param pathToFolderMonitor - папка в которой ловятся файлы
     * @param treatment           - обработчик файлов
     */
    public FileMonitor(String pathToFolderMonitor, Treatment<Runnable> treatment) {

        this.pathMonitor = pathToFolderMonitor;
        this.treatment = treatment;

        appConfig = AppConfigInstance.getConfig();
    }

    public void run() {
        try {
            LOG.info("start file monitor in folder: " + pathMonitor);

            WatchService watcher = FileSystems.getDefault().newWatchService();

            Path dir = new File(pathMonitor).toPath();

            for (; ; ) {

                if (isStopMonitor.get()) break;

                watchKey = dir.register(watcher, ENTRY_CREATE);

                for (WatchEvent<?> event : watchKey.pollEvents()) {

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    Path child = dir.resolve(filename);

                    if (child
                            .toFile()
                            .getName()
                            .endsWith(appConfig.fileExtension())) {


                        Runnable runnable =
                                new FileTreatmentRunnable(
                                        child,
                                        new FileMoverException());
                        treatment.treatment(runnable);
                    } else {
                        LOG.info(
                                "the file [" +
                                        child.toFile().getAbsolutePath() +
                                        "] has no " +
                                        appConfig.fileExtension() +
                                        " extension");
                    }
                }

                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            LOG.error("file monitor error", ex);
        }

    }

    @Override
    public void stop() {
        isStopMonitor.set(true);
        if (watchKey != null) {
            watchKey.cancel();
        }

        LOG.info("file monitor [" + pathMonitor + "] is stop");
    }
}
