package it.sia.jdg.monitor.model.cacheContainer;

import it.sia.helper.Constants;
import java.time.LocalDateTime;


public class JDG65CacheContainerNodeMonitor extends CacheContainerNodeMonitor {

    private String cacheContainerStatus;
    private String cacheContainerRunningCaches;

    public JDG65CacheContainerNodeMonitor(LocalDateTime monitorTimestamp, int clusterSize, String clusterView, String currentNodeName,
            String cacheContainerStatus, String cacheContainerRunningCaches) {
        super.setMonitorTimestamp(monitorTimestamp);
        super.setMonitorExists(true);
        super.setClusterSize(clusterSize);
        super.setCurrentNodeName(currentNodeName);
        this.cacheContainerStatus = cacheContainerStatus;
        this.cacheContainerRunningCaches = cacheContainerRunningCaches;
    }

    public JDG65CacheContainerNodeMonitor() {
    }

    public String getCacheContainerStatus() {
        return cacheContainerStatus;
    }

    public void setCacheContainerStatus(String cacheContainerStatus) {
        this.cacheContainerStatus = cacheContainerStatus;
    }

    public String getCacheContainerRunningCaches() {
        return cacheContainerRunningCaches;
    }

    public void setCacheContainerRunningCaches(String cacheContainerRunningCaches) {
        this.cacheContainerRunningCaches = cacheContainerRunningCaches;
    }

 
    @Override
    public String convertToCSV() {
        return super.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getClusterSize() + Constants.COMMA +
            super.getCurrentNodeName() + Constants.COMMA +
            this.getCacheContainerStatus() + Constants.COMMA +
            this.getCacheContainerRunningCaches();
    }    
}