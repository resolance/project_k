package ru.ezhov.monitor.utils;

import java.util.logging.Logger;

import com.google.common.base.MoreObjects;

/**
 * @author ezhov_da
 */
public class DataJsonObjectMonitor {
    private static final Logger LOG = Logger.getLogger(DataJsonObjectMonitor.class.getName());

    private String ip;
    private String uptime;
    private double loadaverage;
    private String sizeHDDFree;
    private int memSize;

    public DataJsonObjectMonitor() {
    }

    public DataJsonObjectMonitor(
            String ip,
            String uptime,
            double loadaverage,
            String sizeHDDFree,
            int memSize) {
        this.ip = ip;
        this.uptime = uptime;
        this.loadaverage = loadaverage;
        this.sizeHDDFree = sizeHDDFree;
        this.memSize = memSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public double getLoadaverage() {
        return loadaverage;
    }

    public void setLoadaverage(double loadaverage) {
        this.loadaverage = loadaverage;
    }

    public String getSizeHDDFree() {
        return sizeHDDFree;
    }

    public void setSizeHDDFree(String sizeHDDFree) {
        this.sizeHDDFree = sizeHDDFree;
    }

    public int getMemSize() {
        return memSize;
    }

    public void setMemSize(int memSize) {
        this.memSize = memSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ip", ip)
                .add("uptime", uptime)
                .add("loadaverage", loadaverage)
                .add("sizeHDDFree", sizeHDDFree)
                .add("memSize", memSize).toString();
    }


}
