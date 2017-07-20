package ru.ezhov.monitor.fileTreatment;

import java.io.File;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.utils.AppConst;

/**
 * Класс, который переименовывает файлы в папке, если они были до обработки
 *
 * @author ezhov_da
 */
public class OldFilesTreatment {
    private static final Logger LOG = Logger.getLogger(OldFilesTreatment.class.getName());

    private final String folderCheck;

    public OldFilesTreatment(String folderCheck) {
        this.folderCheck = folderCheck;
    }

    public void rename() {
        LOG.info("move start files in folder: " + folderCheck);

        File file = new File(folderCheck);
        File[] files = file.listFiles((dir, name) -> name.endsWith(AppConst.FILE_EXTENSION));

        LOG.info("find " + files.length + " files");

        for (File f : files) {
            File newFile =
                    new File(
                            AppConst.errorFolderPath(folderCheck) +
                                    File.separator +
                                    AppConst.PRE_FILE +
                                    f.getName());

            ErrorFileMover  errorFileMover=
                    new ErrorFileMover(f, newFile, AppConst.ATTEMPTS_COUNT);
            errorFileMover.move();
        }

        LOG.info("move stop");
    }
}
