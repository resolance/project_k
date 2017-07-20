package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.utils.AppConst;

/**
 * Класс, который проверяет наличие старых файлов или файлов,
 * который по каким либо причинам не обработались
 * в папке и заносит их в БД время от времени
 * <p>
 *
 * @author ezhov_da
 */
public class FileLoaderTask extends TimerTask {
    private static final Logger LOG = Logger.getLogger(FileLoaderTask.class.getName());

    private String folderCheck;

    public FileLoaderTask(String folderCheck) {
        this.folderCheck = folderCheck;
    }

    @Override
    public void run() {
        LOG.info("start task load");

        File file = new File(AppConst.errorFolderPath(folderCheck));
        File[] files = file.listFiles((dir, name) -> name.endsWith(AppConst.FILE_EXTENSION)
                && name.startsWith(AppConst.PRE_FILE));

        for (File f : files) {
            LOG.info("execute loader: " + f.getAbsolutePath());

            FileTreatment fileTreatment = new FileTreatment(f.toPath());
            fileTreatment.run();

        }

        LOG.info("end task loader");
    }
}
