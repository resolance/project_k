package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * Класс, который переносит файлы из основной папки обработки в папку с ошибочными файлами
 */

public class FileMoverException implements FileMover {

    private static final Logger LOG = Logger.getLogger(FileMoverException.class.getName());

    private File src;
    private File dist;
    private int countAttempt;

    public void move(File src, File dist, int countAttempt) throws Exception {
        this.src = src;
        this.dist = dist;
        this.countAttempt = countAttempt;

        int attempts = 1;
        moveWithAttempts(attempts);
    }

    private void moveWithAttempts(int attemptsNow) throws Exception {
        if (attemptsNow < countAttempt) {
            try {
                LOG.info(
                        "try move file: [" +
                                src.getAbsolutePath() +
                                "] to [" +
                                dist.getAbsolutePath() +
                                "] attempt № " +
                                attemptsNow);

                Files.move(src.toPath(), dist.toPath(), REPLACE_EXISTING);

                LOG.info(
                        "move file complete: [" +
                                src.getAbsolutePath() +
                                "] to [" +
                                dist.getAbsolutePath() +
                                "] attempt № " +
                                attemptsNow);

                return;

            } catch (IOException e) {
                LOG.error("error move file: [" + src.getAbsolutePath() + "] attempt № " + attemptsNow++, e);
                moveWithAttempts(attemptsNow);
            }
        } else {
            LOG.fatal("fatal move file: [" + src.getAbsolutePath() + "] after attempts ");
            throw new Exception("Can't move file [" + src.getAbsolutePath() + "]");
        }
    }

}
