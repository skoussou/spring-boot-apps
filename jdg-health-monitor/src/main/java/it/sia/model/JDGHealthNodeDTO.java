package it.sia.model;

import it.sia.jdg.monitor.model.cacheContainer.CacheContainerNodeMonitor;
import it.sia.jdg.monitor.model.transport.HotRodTransportMonitor;
import java.io.Serializable;



public class JDGHealthNodeDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private CacheContainerNodeMonitor jdgNodeMonitor;

    private HotRodTransportMonitor hotRodMonitor;

    public JDGHealthNodeDTO(CacheContainerNodeMonitor ccMonitor, HotRodTransportMonitor hotRodMonitor) {
        this.jdgNodeMonitor = ccMonitor;
        this.hotRodMonitor = hotRodMonitor;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public CacheContainerNodeMonitor getJdgNodeMonitor() {
        return jdgNodeMonitor;
    }

    public void setJdgNodeMonitor(CacheContainerNodeMonitor ccMonitor) {
        this.jdgNodeMonitor = ccMonitor;
    }

    public HotRodTransportMonitor getHotRodMonitor() {
        return hotRodMonitor;
    }

    public void setHotRodMonitor(HotRodTransportMonitor hotRodMonitor) {
        this.hotRodMonitor = hotRodMonitor;
    }

}