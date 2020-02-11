package it.sia.jdg.alerts.model.thresholds;

public class JDG65AlertsThresholdsContainer extends JDGAlertsThresholdsContainer {

  // public JDG65AlertsThresholdsContainer(Long
  // minNodePercentageFreeMemoryThreshold,
  // Integer minHotRodFreeThreadsThreshold, Long
  // maxCacheReadLatencyMillisThreshold,
  // Long maxCacheWriteLatencyMillisThreshold) {
  // super(minNodePercentageFreeMemoryThreshold, minHotRodFreeThreadsThreshold,
  // maxCacheReadLatencyMillisThreshold,
  // maxCacheWriteLatencyMillisThreshold);
  // }

  public JDG65AlertsThresholdsContainer(Long minNodePercentageFreeMemoryThreshold,
      Integer minHotRodFreeThreadsThreshold, Long maxCacheReadLatencyMillisThreshold,
      Long maxCacheWriteLatencyMillisThreshold, Integer minClusterSizeThreshold, String nodeHealthStatusThreshold) {
    super(minNodePercentageFreeMemoryThreshold, minHotRodFreeThreadsThreshold, maxCacheReadLatencyMillisThreshold,
        maxCacheWriteLatencyMillisThreshold, minClusterSizeThreshold, nodeHealthStatusThreshold);
  }

  @Override
  public String toString() {
    return "JDG65AlertsThresholdsContainer [" + super.toString() + "]";
  }

}