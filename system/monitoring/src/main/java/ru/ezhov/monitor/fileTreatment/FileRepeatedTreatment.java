package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.nio.file.Path;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfig;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.PathConstructor;

/**
 * Класс, который проверяет наличие файлов,
 * которые по каким либо причинам не обработались
 * и находятся в папке повтора  и либо повторно обрабатывает, либо отправляет в папку с ошибками
 * <p>
 *
 * @author ezhov_da
 */
public class FileRepeatedTreatment extends TimerTask {
    private static final Logger LOG = Logger.getLogger(FileRepeatedTreatment.class.getName());

    private final String folderCheck;
    private final Treatment<Runnable> pathTreatment;

    private final AppConfig appConfig;

    public FileRepeatedTreatment(String folderCheck, Treatment<Runnable> pathTreatment) {
        this.folderCheck = folderCheck;
        this.pathTreatment = pathTreatment;

        appConfig = AppConfigInstance.getConfig();
    }

    @Override
    public void run() {
        LOG.info("start repeat task load");

        File file = new File(new PathConstructor(folderCheck).constructExceptionPathFolder());
        File[] files = file.listFiles((dir, name) -> name.endsWith(appConfig.fileExtension()));

        for (File f : files) {
            LOG.info("execute loader: " + f.getAbsolutePath());
            pathTreatment.treatment(
                    new FileTreatmentRunnable(
                            f.toPath(),
                            new FileMoverError(
                                    new FileMoverException()
                            )
                    )
            );
        }

        LOG.info("end repeat task loader");
    }
}
