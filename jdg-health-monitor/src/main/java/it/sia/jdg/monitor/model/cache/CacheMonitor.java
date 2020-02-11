package it.sia.jdg.monitor.model.cache;

import it.sia.jdg.monitor.model.Monitor;
import java.time.LocalDateTime;


public abstract class CacheMonitor implements Monitor {

    private Boolean monitorExists;
    private LocalDateTime monitorTimestamp;
    private String cacheName;
    private String cacheRebalancingStatus;
    private Long clusteredCacheAvgReadTimeMillis;
    private Long clusteredCacheAvgWriteTimeMillis;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheRebalancingStatus() {
        return cacheRebalancingStatus;
    }

    public void setCacheRebalancingStatus(String cacheRebalancingStatus) {
        this.cacheRebalancingStatus = cacheRebalancingStatus;
    }

    public Long getClusteredCacheAvgReadTimeMillis() {
        return clusteredCacheAvgReadTimeMillis;
    }

    public void setClusteredCacheAvgReadTimeMillis(Long clusteredCacheAvgReadTimeMillis) {
        this.clusteredCacheAvgReadTimeMillis = clusteredCacheAvgReadTimeMillis;
    }

    public Long getClusteredCacheAvgWriteTimeMillis() {
        return clusteredCacheAvgWriteTimeMillis;
    }

    public void setClusteredCacheAvgWriteTimeMillis(Long clusteredCacheAvgWriteTimeMillis) {
        this.clusteredCacheAvgWriteTimeMillis = clusteredCacheAvgWriteTimeMillis;
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