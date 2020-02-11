package it.sia.jdg.monitor.model.transport;

import it.sia.helper.Constants;
import java.time.LocalDateTime;



public class JDG65HotRodTransportMonitor extends HotRodTransportMonitor {

    /* JDG 6 Parameter */
    private int numberOfLocalConnections;

    public JDG65HotRodTransportMonitor() {
    }

    public JDG65HotRodTransportMonitor(LocalDateTime monitorTimestamp, int numberOfLocalConnections, String workerThreads) {
        this.numberOfLocalConnections = numberOfLocalConnections;
        super.setHotRodThreads(Integer.valueOf(workerThreads));
        super.setMonitorExists(true);
        super.setMonitorTimestamp(monitorTimestamp);
    }

    public int getNumberOfLocalConnections() {
        return numberOfLocalConnections;
    }

    public void setNumberOfLocalConnections(int numberOfLocalConnections) {
        this.numberOfLocalConnections = numberOfLocalConnections;
    }

    @Override
    public String convertToCSV() {
        return this.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getHotRodThreads() + Constants.COMMA +
            this.getNumberOfLocalConnections();
    }
}