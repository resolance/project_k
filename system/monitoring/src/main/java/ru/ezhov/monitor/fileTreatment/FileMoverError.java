package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;

import java.io.File;

/**
 * Класс декоратор, который в случае невозможности повторной обработки,
 * перемещает файл в папку с ошибкой
 */
public class FileMoverError implements FileMover {

    private static final Logger LOG = Logger.getLogger(FileMoverError.class);

    private File src;
    private File dist;
    private int countAttempt;
    private FileMover fileMover;
    private AppConfig appConfig;

    public FileMoverError(FileMover fileMover) {
        this.fileMover = fileMover;
        appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public void move(File src, File dist, int countAttempt) throws Exception {
        try {
            fileMover.move(src, dist, countAttempt);
        } catch (Exception ex) {
            LOG.error(
                    "error move file [" +
                            src.getAbsolutePath() +
                            "] to " +
                            appConfig.folderExceptionFile() +
                            " folder. Try move to " +
                            appConfig.folderErrorFile() +
                            " folder.");
            move();
        }
    }

    private void move() {
        File fileDist = new File(
                src.getParentFile().getAbsolutePath() +
                        File.separator +
                        appConfig.folderErrorFile() +
                        dist.getName()
        );

        try {
            fileMover.move(src, fileDist, countAttempt);
        } catch (Exception e) {
            LOG.fatal(
                    "Can't move file  to " +
                            appConfig.folderErrorFile() +
                            " [" + src.getAbsolutePath() +
                            "]", e);
        }
    }
}