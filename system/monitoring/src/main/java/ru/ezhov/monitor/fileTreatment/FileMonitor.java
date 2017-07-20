package ru.ezhov.monitor.fileTreatment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.apache.log4j.Logger;
import ru.ezhov.monitor.utils.AppUtils;
import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;

/**
 * Класс, который следит за изменением в файловой системе
 * и запускает обработку файлов
 * <p>
 * @author ezhov_da
 */
public class FileMonitor implements Runnable
{
    private static final Logger LOG = Logger.getLogger(FileMonitor.class.getName());
    private String pathMonitor;

    public FileMonitor(String pathToFolderMonitor)
    {
        this.pathMonitor = pathToFolderMonitor;
    }

    public void run()
    {
        try
        {
            LOG.info("start file monitor in folder: " + pathMonitor);

            WatchService watcher = FileSystems.getDefault().newWatchService();

            Path dir = new File(pathMonitor).toPath();

            for (;;)
            {
                WatchKey key;
                key = dir.register(watcher,
                        ENTRY_CREATE,
                        ENTRY_DELETE,
                        ENTRY_MODIFY);

                for (WatchEvent<?> event : key.pollEvents())
                {
                    WatchEvent.Kind<?> kind = event.kind();

                    if ("ENTRY_CREATE".equals(kind.name()))
                    {
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();

                        Path child = dir.resolve(filename);

                        if (child.toFile().getName().endsWith(AppUtils.FILE_EXTENSION))
                        {
                            Treatment<Path> fileTreatment = new FileTreatment(child);
                            Thread thread = new Thread(fileTreatment);
                            thread.setDaemon(true);
                            thread.start();
                        }
                    }

                }

                boolean valid = key.reset();
                if (!valid)
                {
                    break;
                }
            }
        } catch (IOException ex)
        {
            LOG.error("file monitor error", ex);
        }
    }

}
