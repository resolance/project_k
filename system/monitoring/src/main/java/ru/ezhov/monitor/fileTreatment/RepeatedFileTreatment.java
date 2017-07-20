package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.util.TimerTask;

import org.apache.log4j.Logger;
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

    public RepeatedFileTreatment(String folderCheck) {
        this.folderCheck = folderCheck;
    }

    @Override
    public void run() {
        LOG.info("start repeat task load");

        File file = new File(AppUtils.errorFolderPath(folderCheck));
        File[] files = file.listFiles((dir, name) -> name.endsWith(AppUtils.FILE_EXTENSION));

        for (File f : files) {
            LOG.info("execute loader: " + f.getAbsolutePath());
            FileTreatment fileTreatment = new FileTreatment(f.toPath());
            fileTreatment.run();
        }

        LOG.info("end repeat task loader");
    }
}
