package ru.ezhov.monitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.utils.AppUtils;

/**
 * @author ezhov_da
 */
public class FileJsonCreator implements Runnable {
    private static final Logger LOG = Logger.getLogger(FileJsonCreator.class.getName());

    private String pathToFolderCreateJSon;
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private String patternIp = "%s.%s.%s.%s";

    public FileJsonCreator(String pathToFolderCreateJSon) {
        this.pathToFolderCreateJSon = pathToFolderCreateJSon;
    }

    public void run() {
        int i = 0;

        LOG.info("start");

        for (; ; ) {

            String val = "";

            try {
                create(i);
            } catch (IOException ex) {
                LOG.error("Не удалось создать файл " + val, ex);
            }

            i++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                //
            }
        }
    }


    private void create(int num) throws IOException {

        LOG.info("start create json file..");


        DataJsonObjectMonitor do1 =
                new DataJsonObjectMonitor("185.159.131.132"
                        , "5:52", 52.00, "20G", 1048576);

        File file = new File(
                pathToFolderCreateJSon
                        + File.separator
                        + dateFormat.format(new Date()) + "_" + String.format(patternIp, num, num, num, num)
                        + AppUtils.FILE_EXTENSION);

        LOG.info("file create: " + file);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.writeValue(file, do1);
    }
}
