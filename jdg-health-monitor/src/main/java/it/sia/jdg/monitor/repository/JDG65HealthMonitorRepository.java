package it.sia.jdg.monitor.repository;

import it.sia.jdg.monitor.model.cache.CacheMonitor;
import it.sia.jdg.monitor.model.cache.JDG65CacheMonitor;
import it.sia.jdg.monitor.model.cacheContainer.CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG65CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.HotRodTransportMonitor;
import it.sia.jdg.monitor.model.transport.JDG65HotRodTransportMonitor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;





@Profile("jdg65")
@Scope(value = "singleton")
@Component
public class JDG65HealthMonitorRepository implements JDGHealthMonitorsRepository {

    private Map<String, CacheMonitor> cachesHealthMonitors = new HashMap<String, CacheMonitor>();

    private JDG65CacheContainerNodeMonitor cachesNodeAndContainerMonitor;

    private JDG65HotRodTransportMonitor hotRodTransportMonitor;

    @Override
    public void addCacheContainerAndNodeHealthMonitors(CacheContainerNodeMonitor monitor) {
        this.cachesNodeAndContainerMonitor = (JDG65CacheContainerNodeMonitor) monitor;
    }

    @Override
    public CacheContainerNodeMonitor getNodeAndCacheContainerMonitor() {
        if (cachesNodeAndContainerMonitor == null) {
            cachesNodeAndContainerMonitor = new JDG65CacheContainerNodeMonitor();
            cachesNodeAndContainerMonitor.setMonitorExists(false);
        }
        return cachesNodeAndContainerMonitor;
    }

    @Override
    public void addCacheHealthMonitors(CacheMonitor monitor) {
        cachesHealthMonitors.put(monitor.getCacheName(), monitor);
    }

    @Override
    public Map<String, CacheMonitor> getAllCachesHealthMonitors() {
        return cachesHealthMonitors;
    }

    @Override
    public CacheMonitor getSingleCacheHealthMonitors(String cacheName) {
        CacheMonitor monitor = cachesHealthMonitors.get(cacheName);
        if (monitor == null) {
            monitor = new JDG65CacheMonitor();
            monitor.setMonitorExists(false);
        }
        return monitor;
    }

    @Override
    public void addCacheHotRodTransportHealthMonitors(HotRodTransportMonitor hotRodTransportMonitor) {
        this.hotRodTransportMonitor = (JDG65HotRodTransportMonitor) hotRodTransportMonitor;
    }

    @Override
    public HotRodTransportMonitor getCacheHotRodTransportHealthMonitors() {
        if (this.hotRodTransportMonitor == null) {
            this.hotRodTransportMonitor = new JDG65HotRodTransportMonitor();
            hotRodTransportMonitor.setMonitorExists(false);
        }
        return this.hotRodTransportMonitor;
    }

}