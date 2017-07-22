package ru.ezhov.monitor.utils;

import com.google.common.base.MoreObjects;

import java.util.Date;


/**
 * Объект, который получает данные из названия файла
 */
public class FileJsonName {
    private Date date;
    private String ip;

    public FileJsonName() {
    }

    public FileJsonName(Date date, String ip) {
        this.date = date;
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .add("ip", ip).toString();
    }
}
