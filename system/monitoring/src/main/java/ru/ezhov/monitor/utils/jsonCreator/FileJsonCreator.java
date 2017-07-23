package ru.ezhov.monitor.utils.jsonCreator;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import ru.ezhov.monitor.beans.DataJsonObjectMonitor;
import ru.ezhov.monitor.utils.AppConfigInstance;

/**
 * @author ezhov_da
 */
public class FileJsonCreator {
    private static final Logger LOG = Logger.getLogger(FileJsonCreator.class.getName());

    private String pathToFolderCreateJSon;
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private String patternIp = "%s.%s.%s.%s";

    public FileJsonCreator(String pathToFolderCreateJSon) {
        this.pathToFolderCreateJSon = pathToFolderCreateJSon;
    }

    public File create(int num) throws IOException {

        LOG.info("start create json file..");

        DataJsonObjectMonitor do1 =
                new DataJsonObjectMonitor("185.159.131.132"
                        , "5:52", 52.00, "20G", 1048576);

        String date = dateFormat.format(new Date());
        String ip = String.format(patternIp, num, num, num, num);

        String extension = AppConfigInstance.getConfig().fileExtension();

        String nameFile = date + "_" + ip + extension;

        File file = new File(
                pathToFolderCreateJSon
                        + File.separator
                        + nameFile);

        LOG.info("file create: " + file);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.writeValue(file, do1);

        return file;
    }
}
