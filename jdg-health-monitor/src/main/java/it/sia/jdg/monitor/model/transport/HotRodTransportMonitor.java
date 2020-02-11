package it.sia.jdg.monitor.model.transport;

import it.sia.jdg.monitor.model.Monitor;
import java.time.LocalDateTime;


public abstract class HotRodTransportMonitor implements Monitor {

    private Boolean monitorExists;
    private LocalDateTime monitorTimestamp;
    private Integer hotRodThreads;

    public Integer getHotRodThreads() {
        return hotRodThreads;
    }

    public void setHotRodThreads(Integer hotRodThreads) {
        this.hotRodThreads = hotRodThreads;
    }

    public Boolean getMonitorExists() {
        return monitorExists;
    }

    public void setMonitorExists(Boolean monitorExists) {
        this.monitorExists = monitorExists;
    }

    public LocalDateTime getMonitorTimestamp() {
        return monitorTimestamp;
    }

    public void setMonitorTimestamp(LocalDateTime monitorTimestamp) {
        this.monitorTimestamp = monitorTimestamp;
    }

    //public abstract String convertToCSV();
}
