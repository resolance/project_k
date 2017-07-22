package ru.ezhov.monitor;

import org.apache.log4j.Logger;


public class FileJSonCreateRunner {

    private static final Logger LOG = Logger.getLogger(FileJSonCreateRunner.class.getName());

    public void run() {
        LOG.info("start");

        for (int i = 0; i < 20; i++) {

            Thread thread = new Thread(()
                    -> new FileJsonCreator("C:\\Users\\rrnezh\\Desktop\\file_monitor"));

            thread.start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                //
            }
        }
    }

}
