package it.sia.jdg.monitor.model.cache;

import it.sia.helper.Constants;
import java.time.LocalDateTime;


public class JDG65CacheMonitor extends CacheMonitor {

    public JDG65CacheMonitor(LocalDateTime monitorTimestamp, String cacheName, String cacheRebalancingStatus, Long clusteredCacheAvgReadTimeMillis,
            Long clusteredCacheAvgWriteTimeMillis) {
        super.setMonitorTimestamp(monitorTimestamp);
        super.setCacheName(cacheName);
        super.setCacheRebalancingStatus(cacheRebalancingStatus);
        super.setClusteredCacheAvgReadTimeMillis(clusteredCacheAvgReadTimeMillis);
        super.setClusteredCacheAvgWriteTimeMillis(clusteredCacheAvgWriteTimeMillis);
    }

    public JDG65CacheMonitor() {
    }

    @Override
    public String convertToCSV() {
        return super.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getCacheName() + Constants.COMMA +
            super.getCacheRebalancingStatus() + Constants.COMMA +
            super.getClusteredCacheAvgReadTimeMillis() + Constants.COMMA +
            super.getClusteredCacheAvgWriteTimeMillis();     
    }

}