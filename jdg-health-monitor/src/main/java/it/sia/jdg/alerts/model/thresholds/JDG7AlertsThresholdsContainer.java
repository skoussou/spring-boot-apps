package it.sia.jdg.alerts.model.thresholds;

public class JDG7AlertsThresholdsContainer extends JDGAlertsThresholdsContainer {

  private Long maxCacheReadLatencyNanosThreshold;

  private Long maxCacheWriteLatencyNanosThreshold;

  public JDG7AlertsThresholdsContainer(Long minNodePercentageFreeMemoryThreshold, Integer minHotRodFreeThreadsThreshold,
      Long maxCacheReadLatencyMillisThreshold, Long maxCacheWriteLatencyMillisThreshold,
      Long maxCacheReadLatencyNanosThreshold, Long maxCacheWriteLatencyNanosThreshold, Integer minClusterSizeThreshold,
      String nodeHealthStatusThreshold) {
    super(minNodePercentageFreeMemoryThreshold, minHotRodFreeThreadsThreshold, maxCacheReadLatencyMillisThreshold,
        maxCacheWriteLatencyMillisThreshold, minClusterSizeThreshold, nodeHealthStatusThreshold);

    this.maxCacheReadLatencyNanosThreshold = maxCacheReadLatencyNanosThreshold;
    this.maxCacheWriteLatencyNanosThreshold = maxCacheReadLatencyNanosThreshold;
  }

  public Long getMaxCacheReadLatencyNanosThreshold() {
    return maxCacheReadLatencyNanosThreshold;
  }

  public void setMaxCacheReadLatencyNanosThreshold(Long maxCacheReadLatencyNanosThreshold) {
    this.maxCacheReadLatencyNanosThreshold = maxCacheReadLatencyNanosThreshold;
  }

  public Long getMaxCacheWriteLatencyNanosThreshold() {
    return maxCacheWriteLatencyNanosThreshold;
  }

  public void setMaxCacheWriteLatencyNanosThreshold(Long maxCacheWriteLatencyNanosThreshold) {
    this.maxCacheWriteLatencyNanosThreshold = maxCacheWriteLatencyNanosThreshold;
  }

  @Override
  public String toString() {
    return "JDG7AlertsThresholdsContainer [maxCacheReadLatencyNanosThreshold=" + maxCacheReadLatencyNanosThreshold
        + ", maxCacheWriteLatencyNanosThreshold=" + maxCacheWriteLatencyNanosThreshold + ", " + super.toString() + "]";
  }

}