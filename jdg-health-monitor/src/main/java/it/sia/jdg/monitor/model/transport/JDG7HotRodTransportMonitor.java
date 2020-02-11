package it.sia.jdg.monitor.model.transport;

import it.sia.helper.Constants;
import java.time.LocalDateTime;




public class JDG7HotRodTransportMonitor extends HotRodTransportMonitor {

    private int activeCount;
    private int largestPoolSize;
    //private int maximumPoolSize;

    public JDG7HotRodTransportMonitor() {
    }

    public JDG7HotRodTransportMonitor(LocalDateTime monitorTimestamp, int activeCount, int largestPoolSize, int maximumPoolSize) {
        this.activeCount = activeCount;
        this.largestPoolSize = largestPoolSize;
        //this.maximumPoolSize = maximumPoolSize;
        super.setHotRodThreads(maximumPoolSize);
        super.setMonitorTimestamp(monitorTimestamp);

        // super.setWorkerThreads(workerThreads);
        super.setMonitorExists(true);
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    public void setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    // public int getMaximumPoolSize() {
    //     return maximumPoolSize;
    // }

    // public void setMaximumPoolSize(int maximumPoolSize) {
    //     this.maximumPoolSize = maximumPoolSize;
    // }


    @Override
    public String convertToCSV() {
        return this.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getHotRodThreads() + Constants.COMMA +
            this.getActiveCount() + Constants.COMMA +
            this.getLargestPoolSize();
    }
}
