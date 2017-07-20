package ru.ezhov.monitor.fileTreatment.interfaces;

/**
 * Обработчик при появлении файла
 *
 * @param <T> - обрабатываемый тип
 */
public abstract class Treatment<T> implements Runnable {
    protected T object;

    public Treatment(T treatmentObject) {
        this.object = treatmentObject;
    }
}
