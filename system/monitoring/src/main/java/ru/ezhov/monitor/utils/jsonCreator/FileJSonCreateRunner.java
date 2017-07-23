package ru.ezhov.monitor.utils.jsonCreator;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Random;


public class FileJSonCreateRunner {

    private static final Logger LOG = Logger.getLogger(FileJSonCreateRunner.class.getName());

    private final int countFiles;
    private final String pathToFolderCreate;

    public FileJSonCreateRunner(int countFiles, String pathToFolderCreate) {
        this.countFiles = countFiles;
        this.pathToFolderCreate = pathToFolderCreate;
    }

    public void run() {

        LOG.info("start json creator");

        for (int i = 0; i < countFiles; i++) {

            try {
                new FileJsonCreator(pathToFolderCreate).create(i);
                LOG.info("create file №: " + (i + 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LOG.info("stop json creator");
    }

}
