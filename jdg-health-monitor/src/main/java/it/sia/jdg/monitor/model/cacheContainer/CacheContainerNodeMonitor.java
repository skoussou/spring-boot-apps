package it.sia.jdg.monitor.model.cacheContainer;

import it.sia.jdg.monitor.model.Monitor;
import java.time.LocalDateTime;


public abstract class CacheContainerNodeMonitor implements Monitor {
 
    private Boolean monitorExists;
    private LocalDateTime monitorTimestamp;
    private int clusterSize;
    private String nodesInClusterView;
    private String currentNodeName;

    public Boolean getMonitorExists() {
        return monitorExists;
    }

    public void setMonitorExists(Boolean monitorExists) {
        this.monitorExists = monitorExists;
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public String getNodesInClusterView() {
        return nodesInClusterView;
    }

    public void setNodesInClusterView(String nodesInClusterView) {
        this.nodesInClusterView = nodesInClusterView;
    }

    public String getCurrentNodeName() {
        return currentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }

    public LocalDateTime getMonitorTimestamp() {
        return monitorTimestamp;
    }

    public void setMonitorTimestamp(LocalDateTime monitorTimestamp) {
        this.monitorTimestamp = monitorTimestamp;
    }

    //public abstract String convertToCSV();
}