package ru.ezhov.monitor.fileTreatment;

import java.io.File;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.PathConstructor;

/**
 * Класс, который перемещает файлы в папку исключений, если они были до обработки,
 * чтоб отдельный поток их обработал
 *
 * @author ezhov_da
 */
public class FileOldTreatment {
    private static final Logger LOG = Logger.getLogger(FileOldTreatment.class.getName());

    private final String folderCheck;

    private final AppConfig appConfig;

    public FileOldTreatment(String folderCheck) {

        this.folderCheck = folderCheck;
        appConfig = AppConfigInstance.getConfig();
    }

    public void rename() {
        LOG.info("move start files in folder: " + folderCheck);

        File file = new File(folderCheck);
        File[] files = file.listFiles((dir, name)
                -> name.endsWith(appConfig.fileExtension()));

        LOG.info("find " + files.length + " files");

        for (File f : files) {
            File newFile =
                    new File(
                            new PathConstructor(folderCheck).constructExceptionPathFolder() +
                                    File.separator +
                                    f.getName());

            FileMoverException fileMoverException =
                    new FileMoverException();

            try {
                fileMoverException.move(f, newFile, appConfig.attemptsCount());
            } catch (Exception e) {
                LOG.error("File [" + f.getAbsolutePath() + "] don't move", e);
            }
        }

        LOG.info("move stop");
    }
}
