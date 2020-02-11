package it.sia.jdg.alerts.model.data;

/**
 * Contains the collected monitors (for JDG7 only) to be checked against
 * thresholds for the generation of alerts
 */
public class JDG7AlertsDataContainer extends JDGAlertsDataContainer {

  private Long cacheReadLatencyNanos;

  private Long cacheWriteLatencyNanos;

  public JDG7AlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads, Integer hotRodUsedThreads,
      Integer clusterSize, String containerHealth) {
    super(nodeFreeMemory, hotRodFreeThreads, hotRodUsedThreads, clusterSize, containerHealth);
  }

  public JDG7AlertsDataContainer(Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis, Long cacheReadLatencyNanos,
      Long cacheWriteLatencyNanos) {
    super();
    super.setCacheReadLatencyMillis(cacheReadLatencyMillis);
    super.setCacheWriteLatencyMillis(cacheWriteLatencyMillis);
    this.cacheReadLatencyNanos = cacheReadLatencyNanos;
    this.cacheWriteLatencyNanos = cacheWriteLatencyNanos;
  }

  public JDG7AlertsDataContainer(Long nodeFreeMemory, Integer hotRodFreeThreads, Integer hotRodUsedThreads,
      Long cacheReadLatencyMillis, Long cacheWriteLatencyMillis, Long cacheReadLatencyNanos,
      Long cacheWriteLatencyNanos, Integer clusterSize, String containerHealth) {
    super(nodeFreeMemory, hotRodFreeThreads, hotRodUsedThreads, cacheReadLatencyMillis, cacheWriteLatencyMillis,
        clusterSize, containerHealth);
    this.cacheReadLatencyNanos = cacheReadLatencyNanos;
    this.cacheWriteLatencyNanos = cacheWriteLatencyNanos;
  }

  public Long getCacheReadLatencyNanos() {
    return cacheReadLatencyNanos;
  }

  public void setCacheReadLatencyNanos(Long cacheReadLatencyNanos) {
    this.cacheReadLatencyNanos = cacheReadLatencyNanos;
  }

  public Long getCacheWriteLatencyNanos() {
    return cacheWriteLatencyNanos;
  }

  public void setCacheWriteLatencyNanos(Long cacheWriteLatencyNanos) {
    this.cacheWriteLatencyNanos = cacheWriteLatencyNanos;
  }

}