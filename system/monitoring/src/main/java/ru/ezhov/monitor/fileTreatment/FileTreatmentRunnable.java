package ru.ezhov.monitor.fileTreatment;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.fileTreatment.interfaces.FileMover;
import ru.ezhov.monitor.utils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class FileTreatmentRunnable implements Runnable {

    private static final Logger LOG = Logger.getLogger(FileTreatment.class.getName());

    private Path treatmentObject;

    private AppConfig appConfig;
    private final FileMover fileMover;

    public FileTreatmentRunnable(Path treatmentObject, FileMover fileMover) {
        this.treatmentObject = treatmentObject;
        appConfig = AppConfigInstance.getConfig();
        this.fileMover = fileMover;
    }

    @Override
    public void run() {

        LOG.info("execute path: " + treatmentObject);

        File file = treatmentObject.toFile();

        try {

            String dataObject = new String(Files.readAllBytes(file.toPath()));

            if ("".equals(dataObject)) {
                LOG.error("file: " + file.getAbsolutePath() + " is empty and not be treatment");
                return;
            }

            LOG.info("read textFrom file:" + dataObject);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            DataJsonObjectMonitor dataJsonObjectMonitor =
                    mapper.readValue(dataObject, DataJsonObjectMonitor.class);


            String nameFile = file.getName();
            FileNamePatternTreatment fileNamePatternTreatment = new FileNamePatternTreatment(nameFile);
            FileJsonName fileJsonName = fileNamePatternTreatment.treatment();

            LOG.info("from file: " + fileJsonName + "\n" +
                    "\tread object: " + dataJsonObjectMonitor);

            if (file.delete()) {
                LOG.info("delete file: " + file);
            } else {
                LOG.error("not delete file: " + file);
            }
        } catch (ParseException | IllegalArgumentException ex) {
            LOG.error("error executed file, because bad name file as : " + file.getAbsolutePath(), ex);
            moveFileOnException(file);
        } catch (Exception ex) {
            LOG.error("error executed file and try move file: " + file.getAbsolutePath(), ex);
            moveFileOnException(file);
        }


    }

    private void moveFileOnException(File file) {
        LOG.info("try move");

        File newFile =
                new File(
                        new PathConstructor(
                                file.getParent()).constructExceptionPathFolder() +
                                File.separator +
                                file.getName());

        try {
            fileMover.move(file, newFile, appConfig.attemptsCount());
        } catch (Exception e) {
            LOG.fatal("Don't move file [" + file.getAbsolutePath() + "] to " + appConfig.folderExceptionFile() + " folder", e);
        }
    }

}
