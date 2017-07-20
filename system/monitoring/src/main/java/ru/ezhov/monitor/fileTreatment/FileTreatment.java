package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.utils.AppConst;
import ru.ezhov.monitor.utils.DataJsonObjectMonitor;
import ru.ezhov.monitor.interfaces.Treatment;

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
         /*   ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            DataJsonObjectMonitor user =
                    mapper.readValue(file, DataJsonObjectMonitor.class);*/

            //LOG.info("read object: " + user);

            if (file.delete()) {
                LOG.info("delete file: " + file);
            } else {
                LOG.error("not delete file: " + file);
            }
        } catch (Exception ex) {
            LOG.error("error executed file: " + file.getAbsolutePath(), ex);
            moveFileOnError(file);
        }
    }

    private void moveFileOnError(File file) {
        LOG.info("try move");

        File newFile =
                new File(
                        AppConst.errorFolderPath(file.getParent()) +
                                File.separator +
                                AppConst.PRE_FILE +
                                file.getName());

        ErrorFileMover errorFileMover =
                new ErrorFileMover(file, newFile, AppConst.ATTEMPTS_COUNT);
        errorFileMover.move();
    }
}
