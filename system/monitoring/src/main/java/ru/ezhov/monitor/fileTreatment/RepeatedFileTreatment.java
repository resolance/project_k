package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.nio.file.Path;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppUtils;

/**
 * Класс, который проверяет наличие файлов,
 * которые по каким либо причинам не обработались
 * и находятся в папке повтора
 * <p>
 *
 * @author ezhov_da
 */
public class RepeatedFileTreatment extends TimerTask {
    private static final Logger LOG = Logger.getLogger(RepeatedFileTreatment.class.getName());

    private String folderCheck;
    private Treatment<Path> pathTreatment;

    public RepeatedFileTreatment(String folderCheck, Treatment<Path> pathTreatment) {
        this.folderCheck = folderCheck;
        this.pathTreatment = pathTreatment;
    }

    @Override
    public void run() {
        LOG.info("start repeat task load");

        File file = new File(AppUtils.errorFolderPath(folderCheck));
        File[] files = file.listFiles((dir, name) -> name.endsWith(AppUtils.FILE_EXTENSION));

        for (File f : files) {
            LOG.info("execute loader: " + f.getAbsolutePath());
            pathTreatment.treatment(f.toPath());
        }

        LOG.info("end repeat task loader");
    }
}
