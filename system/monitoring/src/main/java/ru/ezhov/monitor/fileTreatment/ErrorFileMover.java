package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * Класс, который переносит файлы из основной папки обработки в папку с ошибочными файлами
 */

public class ErrorFileMover {

    private static final Logger LOG = Logger.getLogger(OldFilesTreatment.class.getName());

    private final File src;
    private final File dist;
    private final int countAttempt;

    public ErrorFileMover(File src, File dist, int countAttempt) {
        this.src = src;
        this.dist = dist;
        this.countAttempt = countAttempt;
    }

    public void move() {
        int attempts = 0;
        renameWithAttempts(attempts);
    }

    private void renameWithAttempts(int attemptsNow) {
        if (attemptsNow < countAttempt) {
            try {
                LOG.info(
                        "try move file: [" +
                                src.getAbsolutePath() +
                                "] to [" +
                                dist.getAbsolutePath() +
                                "] attempt № " +
                                ++attemptsNow);

                Files.move(src.toPath(), dist.toPath(), REPLACE_EXISTING);

                LOG.info(
                        "move file complete: [" +
                                src.getAbsolutePath() +
                                "] to [" +
                                dist.getAbsolutePath() +
                                "] attempt № " +
                                ++attemptsNow);

                return;

            } catch (IOException e) {
                LOG.error("error move file: [" + src.getAbsolutePath() + "] attempt № " + ++attemptsNow);
                renameWithAttempts(attemptsNow);
            }
        } else {

            LOG.fatal("error move file: [" + src.getAbsolutePath() + "] after attempts № " + attemptsNow);
        }
    }

}
