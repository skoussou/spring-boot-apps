package it.sia.jdg.monitor.repository;

import it.sia.jdg.monitor.model.cache.CacheMonitor;
import it.sia.jdg.monitor.model.cache.JDG7CacheMonitor;
import it.sia.jdg.monitor.model.cacheContainer.CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.cacheContainer.JDG7CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.HotRodTransportMonitor;
import it.sia.jdg.monitor.model.transport.JDG7HotRodTransportMonitor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Profile("jdg7")
@Scope(value = "singleton")
@Component
public class JDG7HealthMonitorRepository implements JDGHealthMonitorsRepository {

    private Map<String, CacheMonitor> cachesHealthMonitors = new HashMap<String, CacheMonitor>();

    private JDG7CacheContainerNodeMonitor cachesNodeAndContainerMonitor;

    private JDG7HotRodTransportMonitor hotRodTransportMonitor;

    @Override
    public void addCacheHealthMonitors(CacheMonitor monitor) {
        // System.out.println(JDG7HealthMonitorRepository.class.getCanonicalName() + "
        // ========> [" + this + "]");
        cachesHealthMonitors.put(monitor.getCacheName(), monitor);
    }

    @Override
    public Map<String, CacheMonitor> getAllCachesHealthMonitors() {
        // System.out.println(JDG7HealthMonitorRepository.class.getCanonicalName() + "
        // ========> [" + this + "]");
        return cachesHealthMonitors;
    }

    @Override
    public CacheMonitor getSingleCacheHealthMonitors(String cacheName) {
        // System.out.println(JDG7HealthMonitorRepository.class.getCanonicalName() + "
        // ========> [" + this + "]");

        CacheMonitor monitor = cachesHealthMonitors.get(cacheName);
        if (monitor == null) {
            monitor = new JDG7CacheMonitor();
            monitor.setMonitorExists(false);
        }
        return monitor;
    }

    @Override
    public CacheContainerNodeMonitor getNodeAndCacheContainerMonitor() {
        System.out.println(JDG7HealthMonitorRepository.class.getCanonicalName()
                + " ====cachesNodeAndContainerMonitor====> [" + this.cachesNodeAndContainerMonitor + "]");

        if (this.cachesNodeAndContainerMonitor == null) {
            this.cachesNodeAndContainerMonitor = new JDG7CacheContainerNodeMonitor();
            this.cachesNodeAndContainerMonitor.setMonitorExists(false);
        }
        return this.cachesNodeAndContainerMonitor;
    }

    @Override
    public void addCacheContainerAndNodeHealthMonitors(CacheContainerNodeMonitor monitor) {
        // System.out.println(JDG7HealthMonitorRepository.class.getCanonicalName() + "
        // ========> [" + this + "]");
        this.cachesNodeAndContainerMonitor = (JDG7CacheContainerNodeMonitor) monitor;
    }

    @Override
    public void addCacheHotRodTransportHealthMonitors(HotRodTransportMonitor hotRodTransportMonitor) {
        this.hotRodTransportMonitor = (JDG7HotRodTransportMonitor) hotRodTransportMonitor;
    }

    @Override
    public HotRodTransportMonitor getCacheHotRodTransportHealthMonitors() {
        if (this.hotRodTransportMonitor == null) {
            this.hotRodTransportMonitor = new JDG7HotRodTransportMonitor();
            this.hotRodTransportMonitor.setMonitorExists(false);
        }
        return this.hotRodTransportMonitor;
    }

}