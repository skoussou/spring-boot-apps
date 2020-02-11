package it.sia.jdg.monitor.model.cache;

import it.sia.helper.Constants;
import java.time.LocalDateTime;


public class JDG7CacheMonitor extends CacheMonitor {

    private Long clusteredCacheAvgReadTimeNanos;
    private Long clusteredCacheAvgWriteTimeNanos;

    public JDG7CacheMonitor(LocalDateTime monitorTimestamp, String cacheName, String cacheRebalancingStatus, Long clusteredCacheAvgReadTimeMillis,
            Long clusteredCacheAvgWriteTimeMillis, Long clusteredCacheAvgReadTimeNanos,
            Long clusteredCacheAvgWriteTimeNanos) {
        super.setMonitorTimestamp(monitorTimestamp);
        super.setCacheName(cacheName);
        super.setMonitorExists(true);
        super.setCacheRebalancingStatus(cacheRebalancingStatus);
        super.setClusteredCacheAvgReadTimeMillis(clusteredCacheAvgReadTimeMillis);
        super.setClusteredCacheAvgWriteTimeMillis(clusteredCacheAvgWriteTimeMillis);
        this.clusteredCacheAvgReadTimeNanos = clusteredCacheAvgReadTimeNanos;
        this.clusteredCacheAvgWriteTimeNanos = clusteredCacheAvgWriteTimeNanos;
    }

    public JDG7CacheMonitor() {
    }

    public Long getClusteredCacheAvgReadTimeNanos() {
        return clusteredCacheAvgReadTimeNanos;
    }

    public void setClusteredCacheAvgReadTimeNanos(Long clusteredCacheAvgReadTimeNanos) {
        this.clusteredCacheAvgReadTimeNanos = clusteredCacheAvgReadTimeNanos;
    }

    public Long getClusteredCacheAvgWriteTimeNanos() {
        return clusteredCacheAvgWriteTimeNanos;
    }

    public void setClusteredCacheAvgWriteTimeNanos(Long clusteredCacheAvgWriteTimeNanos) {
        this.clusteredCacheAvgWriteTimeNanos = clusteredCacheAvgWriteTimeNanos;
    }

    @Override
    public String convertToCSV() {
        return super.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getCacheName() + Constants.COMMA +
            super.getCacheRebalancingStatus() + Constants.COMMA +
            super.getClusteredCacheAvgReadTimeMillis() + Constants.COMMA +
            super.getClusteredCacheAvgWriteTimeMillis() + Constants.COMMA +
            this.getClusteredCacheAvgWriteTimeNanos() + Constants.COMMA + 
            this.getClusteredCacheAvgWriteTimeNanos();        
    }
}