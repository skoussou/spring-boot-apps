package it.sia.jdg.monitor.model.cacheContainer;

import it.sia.helper.Constants;
import java.time.LocalDateTime;



public class JDG7CacheContainerNodeMonitor extends CacheContainerNodeMonitor {

    private String clusterHealth;
    private Long freeMemoryKbOnNode;
    private Long totalMemoryKbOnNode;
    private String availableMemoryOnNodePercent;
    private Long availableMemoryOnNodeNum;


    public JDG7CacheContainerNodeMonitor(LocalDateTime monitorTimestamp, int numberOfNodes, String nodesInCluster, String currentNodeName,
            String clusterHealth, Long freeMemoryKbOnNode, Long totalMemoryKbOnNode) {
        super.setMonitorTimestamp(monitorTimestamp);
        super.setMonitorExists(true);
        super.setClusterSize(numberOfNodes);
        super.setNodesInClusterView(nodesInCluster);
        super.setCurrentNodeName(currentNodeName);
        this.clusterHealth = clusterHealth;
        this.freeMemoryKbOnNode = freeMemoryKbOnNode;
        this.totalMemoryKbOnNode = totalMemoryKbOnNode;
        caclAvailableMemoryOnNode(totalMemoryKbOnNode, freeMemoryKbOnNode);
    }

    public JDG7CacheContainerNodeMonitor() {
    }

    public String getClusterHealth() {
        return clusterHealth;
    }

    public void setClusterHealth(String clusterHealth) {
        this.clusterHealth = clusterHealth;
    }

    public Long getFreeMemoryKbOnNode() {
        return freeMemoryKbOnNode;
    }

    public void setFreeMemoryKbOnNode(Long freeMemoryKbOnNode) {
        this.freeMemoryKbOnNode = freeMemoryKbOnNode;
    }

    public Long getTotalMemoryKbOnNode() {
        return totalMemoryKbOnNode;
    }

    public void setTotalMemoryKbOnNode(Long totalMemoryKbOnNode) {
        this.totalMemoryKbOnNode = totalMemoryKbOnNode;
    }

    public String getAvailableMemoryOnNodePercent() {
        return availableMemoryOnNodePercent;
    }
    public Long getAvailableMemoryOnNodeNum() {
        return availableMemoryOnNodeNum;
    }

    public void caclAvailableMemoryOnNode(Long totalMemoryKbOnNode, Long freeMemoryKbOnNode) {
        System.out.println("++++++++++++[" + freeMemoryKbOnNode + "]-[" + totalMemoryKbOnNode + "]+++++++++++++++++"
                + ((freeMemoryKbOnNode * 100) / totalMemoryKbOnNode) + "++++++++++++++++++++++++++++++++++++");
            this.availableMemoryOnNodeNum = ((freeMemoryKbOnNode * 100) / totalMemoryKbOnNode);
            this.availableMemoryOnNodePercent = this.availableMemoryOnNodeNum + "%";
    }

 
    @Override
    public String convertToCSV() {
        return super.getMonitorTimestamp().toString()+ Constants.COMMA +
            super.getClusterSize() + Constants.COMMA +
            super.getCurrentNodeName() + Constants.COMMA +
            this.getClusterHealth() + Constants.COMMA +
            this.getTotalMemoryKbOnNode() + Constants.COMMA +
            this.getFreeMemoryKbOnNode() + Constants.COMMA + 
            this.getAvailableMemoryOnNodeNum();
    }
}