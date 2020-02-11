package it.sia.jdg.monitor.repository;

import it.sia.jdg.monitor.model.cache.CacheMonitor;
import it.sia.jdg.monitor.model.cacheContainer.CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG65CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG7CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.HotRodTransportMonitor;
import it.sia.jdg.monitor.model.transport.JDG65HotRodTransportMonitor;
import java.util.Map;


public interface JDGHealthMonitorsRepository {

    /**
     * Adds & Updates the Cache Container and Node Monitors
     * 
     * @param cacheName
     * @return
     */
    void addCacheContainerAndNodeHealthMonitors(CacheContainerNodeMonitor monitor);

    /**
     * Gets the JDG Node and CacheContainer Health Monitors
     * 
     * @return
     */
    public CacheContainerNodeMonitor getNodeAndCacheContainerMonitor();

    /**
     * Adds & Updates the Cache Monitors
     * 
     * @param cacheName
     * @return
     */
    void addCacheHealthMonitors(CacheMonitor monitor);

    /**
     * Returns the details of the Monitoring on all Caches
     * 
     * @return
     */
    Map<String, CacheMonitor> getAllCachesHealthMonitors();

    /**
     * Returns the details of the Monitoring on a single Cache
     * 
     * @param cacheName
     * @return
     */
    CacheMonitor getSingleCacheHealthMonitors(String cacheName);

    /**
     * Adds & Updates details of the monitoring for the HotRod Transport
     * 
     * @param jdg65HotRodTransportMonitor
     */
    void addCacheHotRodTransportHealthMonitors(HotRodTransportMonitor hotRodTransportMonitor);

    /**
     * Returns the details of the HotRod Trnasport Monitoring
     * 
     * @return
     */
    HotRodTransportMonitor getCacheHotRodTransportHealthMonitors();
}