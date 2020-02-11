package it.sia.jdg.alerts.model.data;

/**
 * Contains the collected monitors to be checked against thresholds for the
 * generation of alerts
 */
public abstract class JDGAlertsDataContainer {

  /* Percent of Free Memory Stored here */
  private Long nodeFreeMemory;

  private Integer hotRodFreeThreads;

  private Integer hotRodUsedThreads;

  private Long cacheReadLatencyMillis;

  private Long cacheWriteLatencyMillis;

  private Integer clusterSize;

  private String containerHealth;

  // public JDGAlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads,
  // Integer hotRodUsedThreads,
  // Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis) {
  // this.nodeFreeMemory = nodeFreeMemory;
  // this.hotRodFreeThreads = hotRodFreeThreads;
  // this.hotRodUsedThreads = hotRodUsedThreads;
  // this.cacheReadLatencyMillis = cacheReadLatencyMillis;
  // this.cacheWriteLatencyMillis = cacheWriteLatencyMillis;
  // }

  public JDGAlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads, Integer hotRodUsedThreads,
      Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis, Integer clusterSize, String containerHealth) {
    this.nodeFreeMemory = nodeFreeMemory;
    this.hotRodFreeThreads = hotRodFreeThreads;
    this.hotRodUsedThreads = hotRodUsedThreads;
    this.cacheReadLatencyMillis = cacheReadLatencyMillis;
    this.cacheWriteLatencyMillis = cacheWriteLatencyMillis;
    this.clusterSize = clusterSize;
    this.containerHealth = containerHealth;
  }

  public JDGAlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads, Integer hotRodUsedThreads,
      Integer clusterSize, String containerHealth) {
    this.nodeFreeMemory = nodeFreeMemory;
    this.hotRodFreeThreads = hotRodFreeThreads;
    this.hotRodUsedThreads = hotRodUsedThreads;
    this.clusterSize = clusterSize;
    this.containerHealth = containerHealth;
  }

  public JDGAlertsDataContainer() {
  }

  public Long getNodeFreeMemory() {
    return nodeFreeMemory;
  }

  public void setNodeFreeMemory(Long nodeFreeMemory) {
    this.nodeFreeMemory = nodeFreeMemory;
  }

  public Integer getHotRodFreeThreads() {
    return hotRodFreeThreads;
  }

  public void setHotRodFreeThreads(Integer hotRodFreeThreads) {
    this.hotRodFreeThreads = hotRodFreeThreads;
  }

  public Long getCacheReadLatencyMillis() {
    return cacheReadLatencyMillis;
  }

  public void setCacheReadLatencyMillis(Long cacheReadLatencyMillis) {
    this.cacheReadLatencyMillis = cacheReadLatencyMillis;
  }

  public Long getCacheWriteLatencyMillis() {
    return cacheWriteLatencyMillis;
  }

  public void setCacheWriteLatencyMillis(Long cacheWriteLatencyMillis) {
    this.cacheWriteLatencyMillis = cacheWriteLatencyMillis;
  }

  public Integer getHotRodUsedThreads() {
    return hotRodUsedThreads;
  }

  public void setHotRodUsedThreads(Integer hotRodUsedThreads) {
    this.hotRodUsedThreads = hotRodUsedThreads;
  }

  public Integer getClusterSize() {
    return clusterSize;
  }

  public void setClusterSize(Integer clusterSize) {
    this.clusterSize = clusterSize;
  }

  public String getContainerHealth() {
    return containerHealth;
  }

  public void setContainerHealth(String containerHealth) {
    this.containerHealth = containerHealth;
  }

}