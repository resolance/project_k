package ru.ezhov.monitor.fileTreatment;

import ru.ezhov.monitor.fileTreatment.interfaces.Treatment;
import ru.ezhov.monitor.utils.AppConfigInstance;
import ru.ezhov.monitor.utils.ErrorFolderCreator;

import java.util.Timer;

public class AppFileMonitorRunner {
    private String folder;

    private Treatment<Runnable> fileTreatment;

    public AppFileMonitorRunner(String folder, Treatment<Runnable> fileTreatment) {
        this.fileTreatment = fileTreatment;
        this.folder = folder;
    }

    public final void runApp() {
        createFoldersExceptionAndError();
        strartOldFilesTreatment();
        startTimerTreatmentFiles();
        startMonitorFiles();
    }

    protected void createFoldersExceptionAndError() {
        ErrorFolderCreator errorFolderCreator = new ErrorFolderCreator(folder);
        errorFolderCreator.checkAndCreateFolderExceptionFiles();
        errorFolderCreator.checkAndCreateFolderErrorFiles();
    }

    protected void strartOldFilesTreatment() {
        FileOldTreatment fileOldTreatment = new FileOldTreatment();
        fileOldTreatment.treatment(folder);
    }

    protected void startTimerTreatmentFiles() {
        Timer timer = new Timer();
        timer.schedule(
                new FileRepeatedTreatment(folder, fileTreatment),
                0,
                AppConfigInstance.getConfig().timeMillisecondsCheckErrorFiles());

    }

    protected void startMonitorFiles() {
        Thread thread = new Thread(new FileMonitorIml(folder, fileTreatment));
        thread.start();
    }


}
