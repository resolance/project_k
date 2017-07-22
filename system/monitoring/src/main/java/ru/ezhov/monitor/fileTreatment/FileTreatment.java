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
public class FileTreatment implements Treatment<Path> {
    private static final Logger LOG = Logger.getLogger(FileTreatment.class.getName());


    @Override
    public void treatment(Path treatmentObject) {
        Thread thread = new Thread(new TreatmentFile(treatmentObject));
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Отдельный поток по обработке файла
     */
    private static final class TreatmentFile implements Runnable {

        private Path treatmentObject;

        TreatmentFile(Path treatmentObject) {
            this.treatmentObject = treatmentObject;
        }

        @Override
        public void run() {

            LOG.info("execute path: " + treatmentObject);

            File file = treatmentObject.toFile();
            if (isRepeatFolder(file) && !isRename(file)) return;

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
                LOG.error("error executed file, because bad name file as : " + file.getAbsolutePath(), ex);
            } catch (Exception ex) {
                LOG.error("error executed file and try move file: " + file.getAbsolutePath(), ex);
                moveFileOnErrorOrRenameNotTreatment(file);
            }


        }

        private void moveFileOnErrorOrRenameNotTreatment(File file) {
            if (isRepeatFolder(file)) {
                if (isRename(file)) {

                    File fileParent = file.getParentFile();
                    File fileRename =
                            new File(
                                    fileParent +
                                            File.separator +
                                            AppUtils.FILE_FINAL_EXCEPTION +
                                            file.getName());
                    boolean rename = file.renameTo(fileRename);
                    if (!rename) {
                        LOG.error("error rename file [" + file.getAbsolutePath() + "] in " + AppUtils.FOLDER_EXCEPTION_FILE + " folder");
                    }
                }
            } else {
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

        private boolean isRepeatFolder(File file) {
            return AppUtils.FOLDER_EXCEPTION_FILE.equals(file.getParentFile().getName());
        }

        private boolean isRename(File file) {
            return !file.getName().contains(AppUtils.FILE_FINAL_EXCEPTION);
        }

    }


}
