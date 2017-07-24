package ru.ezhov.monitor.fileTreatment;

import java.io.File;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.PathConstructor;

/**
 * Класс, который перемещает файлы в папку исключений, если они были до обработки,
 * чтоб отдельный поток их обработал
 *
 * @author ezhov_da
 */
public class FileOldTreatment implements Treatment<String> {
    private static final Logger LOG = Logger.getLogger(FileOldTreatment.class.getName());

    private final AppConfig appConfig;

    public FileOldTreatment() {
        appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public void treatment(String folderTreatmentAbsolutePath) {
        LOG.info("treatment start files in folder: " + folderTreatmentAbsolutePath);

        File file = new File(folderTreatmentAbsolutePath);
        File[] files = file.listFiles((dir, name)
                -> name.endsWith(appConfig.fileExtension()));

        LOG.info("find " + files.length + " files");

        for (File f : files) {
            File newFile =
                    new File(
                            new PathConstructor(folderTreatmentAbsolutePath)
                                    .constructExceptionPathFolder() +
                                    File.separator +
                                    f.getName());

            FileMoverException fileMoverException =
                    new FileMoverException();

            try {
                fileMoverException.move(f, newFile, appConfig.attemptsCount());
            } catch (Exception e) {
                LOG.error("File [" + f.getAbsolutePath() + "] don't treatment", e);
            }
        }

        LOG.info("treatment stop");
    }
}
