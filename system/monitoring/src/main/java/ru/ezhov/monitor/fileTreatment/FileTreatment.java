package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.utils.AppUtils;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.FileJsonName;
import ru.ezhov.monitor.utils.FilePatternTreatment;

/**
 * Класс, который отвечает за обработку файлов, а именно внесение в БД и т.д.
 * <p>
 *
 * @author ezhov_da
 */
public class FileTreatment extends Treatment<Path> {
    private static final Logger LOG = Logger.getLogger(FileTreatment.class.getName());

    public FileTreatment(Path path) {
        super(path);
    }

    @Override
    public void run() {

        LOG.info("execute path: " + object);

        File file = object.toFile();

        try {

            String dataObject = new String(Files.readAllBytes(file.toPath()));

            if ("".equals(dataObject)) return;

            LOG.info("read textFrom file:" + dataObject);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            DataJsonObjectMonitor dataJsonObjectMonitor =
                    mapper.readValue(dataObject, DataJsonObjectMonitor.class);


            String nameFile = file.getName();
            FilePatternTreatment filePatternTreatment = new FilePatternTreatment();
            FileJsonName fileJsonName = filePatternTreatment.treatment(nameFile);

            LOG.info("from file: " + fileJsonName + "\n" +
                    "\tread object: " + dataJsonObjectMonitor);

            if (file.delete()) {
                LOG.info("delete file: " + file);
            } else {
                LOG.error("not delete file: " + file);
            }
        } catch (ParseException | IllegalArgumentException ex) {
            LOG.error("error executed file, because bad name as : " + file.getAbsolutePath(), ex);
        } catch (Exception ex) {
            LOG.error("error executed file: " + file.getAbsolutePath(), ex);
            moveFileOnError(file);
        }
    }

    private void moveFileOnError(File file) {
        LOG.info("try move");

        File newFile =
                new File(
                        AppUtils.errorFolderPath(file.getParent()) +
                                File.separator +
                                file.getName());

        ErrorFileMover errorFileMover =
                new ErrorFileMover(file, newFile, AppUtils.ATTEMPTS_COUNT);
        errorFileMover.move();
    }
}
